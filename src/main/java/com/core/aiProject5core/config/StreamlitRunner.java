package com.core.aiProject5core.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StreamlitRunner implements ApplicationRunner {
	
	// 가상환경에서 설치해야 함
	// pip install google-genai reportlab openpyxl
	// pip install streamlit google-genai python-dotenv
	// pip install openpyxl 


	// 5core를 구동하면서 streamlit을 함께 구동하도록 설정
	// - 5core와 ai-server 두 폴더가 같은 상위 폴더에 위치해야 함
	// - 예를 들어 c드라이브의 git폴더 안에 5core폴더, ai-server폴더가 함께 있어야 함
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 현재 5core 프로젝트 위치
		Path projectDir = Paths.get("").toAbsolutePath();
		
		// ai-server 폴더 위치
		// - 상위 폴더가 같으므로 getParent()를 통해서 찾을 수 있음
		Path aiServerDir = projectDir.resolve("ai-server");
		
		// venv 폴더의 python.exe 경로 (윈도우 기준)
		Path pythonPath = aiServerDir
				.resolve(".venv")
				.resolve("Scripts")
				.resolve("python.exe");
		
		if(!Files.exists(pythonPath)) {
			System.err.println("python.exe가 존재하지 않음");
			return;
		}
		
		if(!Files.exists(aiServerDir.resolve("ai_for_dealer.py"))) {
			System.err.println("ai_for_dealer.py가 존재하지 않음");
			return;
		}
		
		if(!Files.exists(aiServerDir.resolve("applyDetail.py"))) {
			System.err.println("applyDetail.py가 존재하지 않음");
			return;
		}
		
		// Streamlit run ai_for_delaer.py --server.baseUrlPath=5core --server.port=8509 커맨드 실행
		ProcessBuilder builder = new ProcessBuilder(
                pythonPath.toString(),
                "-m", "streamlit", "run",
                "ai_for_dealer.py",
                "--server.baseUrlPath=5core",
                "--server.port=8509"
        );
		
        // applyDetailRecommend: 상담 상세 차량 추천 (8502)
		ProcessBuilder applyDetailRecommend = new ProcessBuilder(
                pythonPath.toString(),
                "-m", "streamlit", "run",
                "applyDetail.py",
                "--server.baseUrlPath=5core",
                "--server.port=8502"
        );
		
        // dealerSaleGraph: 판매 실적 그래프 (8503)
		ProcessBuilder dealerSaleGraph = new ProcessBuilder(
		    pythonPath.toString(), "-m", "streamlit", "run",
		    "sales_graph.py",
		    "--server.baseUrlPath=5core",
		    "--server.port=8503"
		);		
		
        // chatbot: 판매 실적 그래프 (8504)
		ProcessBuilder chatbot = new ProcessBuilder(
		    pythonPath.toString(), "-m", "streamlit", "run",
		    "chatbot_streamlit.py",
		    "--server.baseUrlPath=5core",
		    "--server.port=8504"
		);		
		
		// modelSaleDashboard: 차량별 판매량 대시보드 (8505)
		ProcessBuilder modelSaleDashboard = new ProcessBuilder(
		    pythonPath.toString(), "-m", "streamlit", "run",
		    "model_sales_dashboard.py",
		    "--server.baseUrlPath=5core",
		    "--server.port=8505"
		);
		
		// monthlySalesGraph: 딜러 월별 판매 그래프 (8506)
		ProcessBuilder monthlySalesGraph = new ProcessBuilder(
		    pythonPath.toString(), "-m", "streamlit", "run",
		    "monthly_sales_graph.py",
		    "--server.baseUrlPath=5core",
		    "--server.port=8506",
		    "--server.headless=true"
		);
		
		// globalSales: 딜러 월별 판매 그래프 (8507)
		ProcessBuilder globalSales = new ProcessBuilder(
		    pythonPath.toString(), "-m", "streamlit", "run",
		    "global_sales_dashboard.py",
		    "--server.baseUrlPath=5core",
		    "--server.port=8507",
		    "--server.headless=true"
		);
		
		// modelSales: 딜러 월별 판매 그래프 (8508)
		ProcessBuilder modelSales = new ProcessBuilder(
		    pythonPath.toString(), "-m", "streamlit", "run",
		    "model_sales_dashboard.py",
		    "--server.baseUrlPath=5core",
		    "--server.port=8508",
		    "--server.headless=true"
		);
		
		// chatbotAdmin: 관리자용 쳇봇 (8510)
		ProcessBuilder chatbotAdmin = new ProcessBuilder(
		    pythonPath.toString(), "-m", "streamlit", "run",
		    "chatbot_admin_streamlit.py",
		    "--server.baseUrlPath=5core",
		    "--server.port=8510",
		    "--server.headless=true"
		);	
		
		// copyGlobal: 수요예측 (8511)
		ProcessBuilder copyGlobal = new ProcessBuilder(
			    pythonPath.toString(), "-m", "streamlit", "run",
			    "copyGlobal.py",
			    "--server.baseUrlPath=5core",
			    "--server.port=8511",
			    "--server.headless=true"
			);	
		
		// AI수요예측
		// aiDomForecast: 국내수요예측 (8512)
		ProcessBuilder aiDomForecast = new ProcessBuilder(
			    pythonPath.toString(), "-m", "streamlit", "run",
			    "aiDomForecast.py",
			    "--server.baseUrlPath=5core",
			    "--server.port=8512",
			    "--server.headless=true"
			);
		
		// AI수요예측
		// aiNaForecast: 북미수요예측 (8513)
		ProcessBuilder aiNaForecast = new ProcessBuilder(
			    pythonPath.toString(), "-m", "streamlit", "run",
			    "aiNaForecast.py",
			    "--server.baseUrlPath=5core",
			    "--server.port=8513",
			    "--server.headless=true"
			);			
		
		// 실행 디렉토리
		builder.directory(aiServerDir.toFile());
		applyDetailRecommend.directory(aiServerDir.toFile());
		dealerSaleGraph.directory(aiServerDir.toFile());
		chatbot.directory(aiServerDir.toFile());  
		modelSaleDashboard.directory(aiServerDir.toFile()); 
		monthlySalesGraph.directory(aiServerDir.toFile()); 
		globalSales.directory(aiServerDir.toFile()); 
		modelSales.directory(aiServerDir.toFile()); 
		chatbotAdmin.directory(aiServerDir.toFile()); 
		copyGlobal.directory(aiServerDir.toFile()); 
		aiDomForecast.directory(aiServerDir.toFile());
		aiNaForecast.directory(aiServerDir.toFile());
		
		// 프로세스 실행
		// - 실행되었다면 console에 streamlit이 실행되었다고 뜸
		builder.start();
		applyDetailRecommend.start();
		dealerSaleGraph.start();
		chatbot.start();
		modelSaleDashboard.start();
		monthlySalesGraph.start();
		globalSales.start();
		modelSales.start();
		chatbotAdmin.start();
		copyGlobal.start();
		aiDomForecast.start();
		aiNaForecast.start();
		System.out.println("Streamlit이 실행됨");
		System.out.println("판매 실적 그래프(딜러) 실행됨");
		System.out.println("판매 실적 그래프(딜러) 실행됨");
		System.out.println("차량별 판매량 대시보드 실행됨");
		System.out.println("딜러 월별 판매 그래프 실행됨");
		System.out.println("고객용 쳇봇 실행됨");
		System.out.println("AI수요예측 실행됨");
		
	}
	

}
