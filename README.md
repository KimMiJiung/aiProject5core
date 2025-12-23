프로젝트명:5core
프로젝트내용:자동차 판매 관리 자동화 및 AI 융합 CRM 플랫폼

[기술스택]
Framework	Spring Boot 3.5.7, Spring Data JPA
Languages	Java 21 (LTS), Python 3.13
Dashboard	Streamlit, Plotly, Matplotlib
Database	MySQL 8.4 (LTS)
Infrastructure	Docker, Amazon ECR, AWS EC2/RDS
Integrated APIs	Google Maps Platform, 전기차 충전소 실시간 정보 API, 표준 행정구역 API
API: Google Maps Platform,표준 행정구역 API, 전기차 충전소 정보API
배포 아키텍처:AWS, ECR, Docker

[기능]
1.관리자 (Admin) - 전략적 의사결정 및 운영
마이페이지 및 프로필CRUD: 관리자 개인 정보 관리 및 보안 설정. 
멤버 관리: 시스템 사용자(관리자, 딜러, 고객) 권한 승인 및 계정 관리.
글로벌 판매 실적: 전 세계 지역별 실시간 판매 현황 모니터링 및 대시보드.
AI 수요 예측: 국내 및 북미 시장 데이터를 기반으로 한 머신러닝 기반 수요 예측 결과 제공.
차량별 판매 실적: 차종/트림별 상세 판매 통계 분석.

2.딜러 (Dealer) - 영업 효율화 및 고객 관리
마이페이지 및 프로필: 영업 사원 정보 및 성과 지표 관리.
상담 신청 관리: 유입된 잠재 고객의 상담 신청 명단 확인 및 상세 요구사항 파악.
상담/구매 관리: 상담 진행 단계(Lead) 관리 및 실제 차량 구매 계약 프로세스 트래킹.

3.고객 (Customer) - 개인화된 구매 여정
마이페이지: 회원 정보 수정, 본인의 상담 신청 리스트 및 과거 구매 내역 통합 조회.
상담 서비스: 신차 구매 상담 신청, 신청 내역 확인 및 상세 내용 수정 기능.

4.메인 서비스 (Main/Public) - 브랜드 경험 및 정보 제공
모델 라인업: 현대자동차 전 차종 카탈로그 및 상세 정보.
블루멤버스: 멤버십 혜택 정보 및 포인트 연동 서비스.
전기차 특화 서비스: 전국 전기차 충전소 위치 및 상태 안내.
고객 지원: 고객센터 연결 및 이벤트/프로모션 정보 제공.

[API]
