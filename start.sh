#!/bin/bash

# 1) Spring Boot 실행
/usr/bin/java -jar /app.jar &

# 2) Streamlit 10개 실행 (Runner 설정 기준)
python3 -m streamlit run /streamlit_app/applyDetail.py             --server.port=8502 --server.address=0.0.0.0 --server.baseUrlPath=5core &
python3 -m streamlit run /streamlit_app/sales_graph.py             --server.port=8503 --server.address=0.0.0.0 --server.baseUrlPath=5core &
python3 -m streamlit run /streamlit_app/chatbot_streamlit.py       --server.port=8504 --server.address=0.0.0.0 --server.baseUrlPath=5core &
python3 -m streamlit run /streamlit_app/monthly_sales_graph.py     --server.port=8506 --server.address=0.0.0.0 --server.baseUrlPath=5core &
python3 -m streamlit run /streamlit_app/global_sales_dashboard.py  --server.port=8507 --server.address=0.0.0.0 --server.baseUrlPath=5core &
python3 -m streamlit run /streamlit_app/model_sales_dashboard.py   --server.port=8508 --server.address=0.0.0.0 --server.baseUrlPath=5core &
python3 -m streamlit run /streamlit_app/ai_for_dealer.py           --server.port=8509 --server.address=0.0.0.0 --server.baseUrlPath=5core &
python3 -m streamlit run /streamlit_app/chatbot_admin_streamlit.py --server.port=8510 --server.address=0.0.0.0 --server.baseUrlPath=5core &
python3 -m streamlit run /streamlit_app/aiDomForecast.py           --server.port=8512 --server.address=0.0.0.0 --server.baseUrlPath=5core &
python3 -m streamlit run /streamlit_app/aiNaForecast.py            --server.port=8513 --server.address=0.0.0.0 --server.baseUrlPath=5core &

# 3) 하나라도 죽으면 컨테이너도 종료되게
wait -n
exit $?
