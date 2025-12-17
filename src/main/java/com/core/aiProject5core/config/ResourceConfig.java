package com.core.aiProject5core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//파일 시스템에 저장된 이미지에 접근하는 환경 설정 클래스
//@Configuration
public class ResourceConfig implements WebMvcConfigurer {
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/images/**")                // 웹요청에 포함되도록 하는 외부 연결 URI 경로
//				.addResourceLocations("file:///" + "c:/5core/")  // 파일 위치
//				.setCachePeriod(60*60*24);                       // 접근 파일 캐싱 시간, 1일
//
//	}

}
