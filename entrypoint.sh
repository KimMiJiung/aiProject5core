#!/bin/bash

# 1) Spring Boot 실행
java -jar /app/app.jar &

# 2) 가상환경 파이썬 경로 설정
VENV_PYTHON=/app/ai-server/venv/bin/python3

# 3) Streamlit 인스턴스 실행 (8511 제외)
# --browser.gatherUsageStats false 옵션을 추가하여 로그를 더 깨끗하게 만듭니다.
$VENV_PYTHON -m streamlit run /app/ai-server/applyDetail.py --server.port=8502 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/sales_graph.py --server.port=8503 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/chatbot_streamlit.py --server.port=8504 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/model_sales_dashboard.py --server.port=8505 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/monthly_sales_graph.py --server.port=8506 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/global_sales_dashboard.py --server.port=8507 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/model_sales_dashboard.py --server.port=8508 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/ai_for_dealer.py --server.port=8509 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/chatbot_admin_streamlit.py --server.port=8510 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/aiDomForecast.py --server.port=8512 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &
$VENV_PYTHON -m streamlit run /app/ai-server/aiNaForecast.py --server.port=8513 --server.address=0.0.0.0 --server.baseUrlPath=5core --browser.gatherUsageStats false &

# 프로세스 유지
wait -n
exit $?