import os
import json
import streamlit as st
import requests
from datetime import datetime
from dotenv import load_dotenv
import google.generativeai as genai
import argparse
from requests.adapters import HTTPAdapter
from urllib3.util.retry import Retry

# [ 1. 5core 연동 기본 설정 ]
load_dotenv()

GEMINI_API_KEY = os.environ.get("GEMINI_API_KEY")

# 호스트 주소는 기존 주소를 유지합니다.
FIVECORE_BASE_URL = os.environ.get("FIVECORE_BASE_URL", "http://localhost:8090/5core")
#FIVECORE_BASE_URL = os.environ.get("FIVECORE_BASE_URL", "http://3.35.97.120:8090/5core")

# API 경로 설정
COUNSELING_DETAIL_PATH = "/api/counseling/{counselingId}"
VEHICLE_CANDIDATE_PATH = "/api/vehicles/ai-candidates"
AI_RECOMMEND_SAVE_PATH = "/api/counseling/{counselingId}/ai-recommendations"
FINAL_CHOICE_SAVE_PATH = "/api/counseling/{counselingId}/final-recommendations"

# [ 2. HTTP 세션 및 재시도 전략 설정 ]
def get_request_session():
    session = requests.Session()
    retry_strategy = Retry(
        total=3,
        backoff_factor=1,
        status_forcelist=[429, 500, 502, 503, 504],
        allowed_methods=["HEAD", "GET", "OPTIONS", "POST"]
    )
    adapter = HTTPAdapter(max_retries=retry_strategy, pool_connections=10, pool_maxsize=20)
    session.mount("http://", adapter)
    session.mount("https://", adapter)
    return session

COMMON_HEADERS = {'Connection': 'close'}

# [ 3. 환경변수, 제미나이 설정 ]
if not GEMINI_API_KEY:
    st.error("API KEY가 설정되어 있지 않습니다.")
    st.stop()

genai.configure(api_key=GEMINI_API_KEY)

# 모델명을 gemini-2.5-flash로 설정
MODEL_NAME = "gemini-2.5-flash"

system_instruction = """
당신은 현대 자동차 딜러입니다.
사용자는 차량 구매 상담을 신청한 고객이며, 5core의 상담(Counseling) 정보를 기반으로 상담을 진행합니다.
항상 JSON 형식으로만 응답합니다.
"""

model = genai.GenerativeModel(
    model_name=MODEL_NAME,
    system_instruction=system_instruction
)

# [ 4. 5core 연동 함수 ]

def build_url(path_template: str, **path_vars) -> str:
    path = path_template.format(**path_vars)
    return f"{FIVECORE_BASE_URL}{path}"

def get_counseling(counseling_id: int):
    url = build_url(COUNSELING_DETAIL_PATH, counselingId=counseling_id)
    session = get_request_session()
    try:
        res = session.get(url, headers=COMMON_HEADERS, timeout=10)
        res.raise_for_status()
        return res.json()
    except Exception as e:
        st.error(f"Counseling 조회 실패: {e}")
        return None
    finally:
        session.close()

def extract_need(counseling_json: dict) -> dict:
    if not counseling_json: return {}
    return {
        "vehicleId": counseling_json.get("vehicleId"),
        "vehicleName": counseling_json.get("vehicleName"),
        "purchasePurpose": counseling_json.get("purchasePurpose"),
        "otherInput": counseling_json.get("otherInput"),
        "vehicleType": counseling_json.get("vehicleType"),
        "engineType": counseling_json.get("engineType")
    }

def get_vehicles(need_info: dict) -> dict:
    url = build_url(VEHICLE_CANDIDATE_PATH)
    params = {
        "vehicleType": need_info.get("vehicleType"),
        "engineType": need_info.get("engineType"),
        "purchasePurpose": need_info.get("purchasePurpose")
    }
    session = get_request_session()
    try: 
        res = session.get(url, params=params, headers=COMMON_HEADERS, timeout=10)
        res.raise_for_status()
        return res.json()
    except Exception as e:
        st.warning(f"차량 후보 조회 실패, 더미 데이터 사용: {e}")
        return [
            {"modelCode": "avante", "name": "아반떼", "vehicleType": "세단", "engineType": "가솔린", "basePrice": 20348000, "fuelType": "휘발유"},
            {"modelCode": "tucson", "name": "투싼", "vehicleType": "SUV", "engineType": "디젤", "basePrice": 32527000, "fuelType": "디젤"},
            {"modelCode": "ioniq5", "name": "아이오닉 5", "vehicleType": "SUV", "engineType": "전기", "basePrice": 54790000, "fuelType": "전기"}
        ]
    finally:
        session.close()

def get_vehicle_image_url(recommends: dict) -> str:
    file_name = recommends.get("fileName")
    return f"{FIVECORE_BASE_URL}/images/{file_name}"

def get_recommended_vehicles_info(recommends: list, candidates: list) -> None:
    small_code = {str(c.get("modelCode")).lower(): c for c in candidates}
    for r in recommends:
        code = str(r.get("modelCode")).lower()
        src = small_code.get(code)
        if src:
            if not r.get("fileName"): r["fileName"] = src.get("fileName")
            if not r.get("finalPrice"): r["finalPrice"] = src.get("finalPrice")

def save_ai_vehicle_list(counseling_id: int, result: dict) -> bool:
    url = build_url(AI_RECOMMEND_SAVE_PATH, counselingId=counseling_id)
    payload = {"saved": datetime.now().isoformat(), "result": result}
    session = get_request_session()
    try:
        res = session.post(url, json=payload, headers=COMMON_HEADERS, timeout=10)
        res.raise_for_status()
        return True
    except Exception as e:
        st.error(f"차량 후보 리스트 저장 실패: {e}")
        return False
    finally:
        session.close()

def save_final_choice(counseling_id: int, choice: dict) -> bool:
    url = build_url(FINAL_CHOICE_SAVE_PATH, counselingId=counseling_id)
    payload = {"saved": datetime.now().isoformat(), "vehicle": choice}
    session = get_request_session()
    try:
        res = session.post(url, json=payload, headers=COMMON_HEADERS, timeout=10)
        res.raise_for_status()
        return True
    except Exception as e:
        st.error(f"최종 추천 차량 저장 실패: {e}")
        return False
    finally:
        session.close()

# [ 5. Gemini 호출 ]

def ask_gemini(counseling_json: dict, need_info: dict, candidates: list, max_count: int = 3) -> dict:
    # 1. 텍스트 변환
    counseling_text = json.dumps(counseling_json, ensure_ascii=False, indent=2)
    need_text = json.dumps(need_info, ensure_ascii=False, indent=2)
    candidates_text = json.dumps(candidates, ensure_ascii=False, indent=2)

    # 2. 프롬프트 정의 (이 변수가 generate_content 바로 위에 있어야 합니다)
    prompt = f"""
다음 정보를 바탕으로 고객에게 적합한 차량을 최대 {max_count}대 추천하십시오.
[상담 정보] {counseling_text}
[고객 요구사항] {need_text}
[후보 리스트] {candidates_text}

반드시 아래 JSON 형식으로만 응답하십시오. 추가 설명은 절대 금지합니다.
{{
    "추천": [
        {{ "modelCode": "코드", "name": "이름", "vehicleType": "타입", "fuelType": "연료", "reason": "추천사유" }}
    ]
}}
"""
    # 3. 모델 호출
    try:
        response = model.generate_content(prompt)
        jText = response.text.strip()
        
        # Markdown 코드 블록 제거 로직
        if jText.startswith("```"):
            lines = jText.splitlines()
            if "json" in lines[0].lower():
                jText = "\n".join(lines[1:-1])
            else:
                jText = "\n".join(lines[1:-1])
        
        data = json.loads(jText)
        
        # 리스트로 반환될 경우 딕셔너리로 래핑
        if isinstance(data, list):
            return {"추천": data}
        return data
        
    except Exception as e:
        st.error(f"Gemini 호출 또는 파싱 실패: {e}")
        return {"추천": []}

# [ 6. Streamlit 설정 및 실행 ]
st.set_page_config(page_title="5core AI 차량 추천", layout="wide")
st.subheader("5core 고객용 AI 차량 추천 (Gemini 2.5 Flash)")

st.markdown("""
    <style>
    .stApp { background-color: #ffffff; color: #000000; }
    .block-container { max-width: 1600px; }
    .ai-card img { display: block; margin: auto; }
    </style>
    """, unsafe_allow_html=True)

parser = argparse.ArgumentParser(add_help=False)
parser.add_argument("--counselingId")
args, _ = parser.parse_known_args()
params = st.query_params
c_id = params.get("counselingId", args.counselingId)

if not c_id:
    st.error("counselingId가 필요합니다.")
    st.stop()
counseling_id = int(c_id)

# 데이터 로딩 및 세션 관리
# [ 6. 데이터 로드 및 세션 관리 로직 ]
# 201라인 부근의 if 문부터 시작하는 블록입니다.

if "recommend_result" not in st.session_state:
    with st.spinner("AI가 고객님을 위한 최적의 차량을 분석 중입니다..."):
        c_data = get_counseling(counseling_id)
        if c_data:
            st.session_state["counseling_json"] = c_data
            needs = extract_need(c_data)
            st.session_state["need_info"] = needs
            
            candis = get_vehicles(needs)
            st.session_state["candidates"] = candis
            
            # 여기서 ask_gemini를 호출합니다.
            result = ask_gemini(c_data, needs, candis)
            
            # 결과 타입 체크
            if isinstance(result, list):
                recommends_list = result
            elif isinstance(result, dict):
                recommends_list = result.get("추천", [])
            else:
                recommends_list = []
            
            # 정보 보정 및 세션 저장
            get_recommended_vehicles_info(recommends_list, candis)
            st.session_state["recommend_result"] = {"추천": recommends_list}
            st.session_state["recommends"] = recommends_list[:3]
            st.session_state["selected_reco_idx"] = 0
        else:
            st.error("상담 정보를 불러오지 못했습니다.")
            st.stop()

recommend_result = st.session_state.get("recommend_result")

if recommend_result:
    recommends = recommend_result.get("추천") or []
    recommends = recommends[:3]
    
    if recommends:
        st.markdown("### AI 추천 결과")
        cols = st.columns(3)

        for idx, r in enumerate(recommends):
            name = r.get("name", "")
            v_type = r.get("vehicleType", "")
            fuel = r.get("fuelType", "")
            price = r.get("finalPrice")

            with cols[idx]:
                st.markdown('<div class="ai-card">', unsafe_allow_html=True)
                img_url = get_vehicle_image_url(r)
                st.image(img_url, width=320)
                st.markdown(f"<p style='font-size:20px; font-weight:700; margin:8px 0 4px 0;'>{name}</p>", unsafe_allow_html=True)
                st.markdown(f"<p style='margin:0; font-size:14px;'>차종: {v_type}</p>", unsafe_allow_html=True)
                st.markdown(f"<p style='margin:0; font-size:14px;'>연료: {fuel}</p>", unsafe_allow_html=True)
                st.markdown(f"<p style='margin:0 0 8px 0; font-size:14px;'>가격: {f'{price:,} 원' if price else '-'}</p>", unsafe_allow_html=True)

                if st.button("이 차량 선택 / 이유 보기", key=f"select_{idx}", use_container_width=True):
                    st.session_state["selected_reco_idx"] = idx
                st.markdown('</div>', unsafe_allow_html=True)

        # 선택된 차량 이유 표시
        selected = recommends[st.session_state["selected_reco_idx"]]
        st.markdown("---")
        st.markdown("### 선택한 차량 이유")
        st.markdown(f"**{selected.get('name', '')} ({selected.get('modelCode', '')})** 에 대한 추천 이유:")
        st.info(selected.get('reason', '이유가 없습니다.'))
    else:
        st.info("추천 결과가 비어 있습니다.")
else:
    st.info("AI 추천 결과가 없습니다.")