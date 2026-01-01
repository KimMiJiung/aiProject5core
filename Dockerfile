FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    python3-venv \
    && rm -rf /var/lib/apt/lists/*

# 1. 작업 디렉토리 설정
WORKDIR /app

# 2. AI 서버 소스 복사
COPY ai-server /app/ai-server

# 3. 가상환경 생성
RUN python3 -m venv /app/ai-server/venv

# 4. 가상환경 내에 패키지 설치
RUN /app/ai-server/venv/bin/pip install --no-cache-dir -r /app/ai-server/requirements.txt

# 3. 나머지 파일 복사
COPY build/libs/aiProject5core-0.0.1-SNAPSHOT.jar app.jar

COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

EXPOSE 8090 8502 8503 8504 8505 8506 8507 8508 8509 8510 8511 8512 8513

# 스크립트를 통해 여러 프로세스 실행
ENTRYPOINT ["/app/entrypoint.sh"]