package com.core.aiProject5core.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class PythonRunner {
	// cmd에서 pip install matplotlib seaborn pandas numpy scipy
    // VSCode에서 확인한 파이썬 풀 경로로 변경 (아래 중 하나 선택)
    private final String pythonCmd = "C:\\Users\\rlaalwjd48\\AppData\\Local\\Programs\\Python\\Python313\\python.exe";

    public PythonRunner() {
        // 위에서 고정값으로 설정했으므로 생성자 비움
    }

    public String runHistogramPy(String name) throws IOException, InterruptedException {
        // 1) 이미지 저장할 폴더 (static/resources에서 서빙)
        String outputDir = Paths.get("src", "main", "resources", "static", "python")
                             .toAbsolutePath().toString();
        
        // 폴더가 없으면 생성
        new java.io.File(outputDir).mkdirs();
        
        // 2) 스크립트 경로
        String scriptPath = Paths.get("src", "main", "resources", "python", "02histogram.py")
                                 .toAbsolutePath().toString();
        
        System.out.println("실행할 파이썬 경로: " + scriptPath);
        System.out.println("이미지 저장 경로: " + outputDir);

        // 3) 프로세스 실행 (outputDir 전달)
        ProcessBuilder pb = new ProcessBuilder(pythonCmd, scriptPath, outputDir);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // 4) 출력 읽기
        try (BufferedReader reader = new BufferedReader(
                     new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("python exit code = " + exitCode + "\n출력:\n" + out.toString());
            }

            // 5) 이미지 URL 반환 (Spring Boot static 서빙)
            return "타이타닉 fare 히스토그램 3개 그래프 저장 완료!\n\n"
                 + "이미지: http://localhost:8090/images/titanic_histogram.png\n\n"
                 + out.toString();
        }
    }
    

}
