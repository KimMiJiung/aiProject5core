package com.core.aiProject5core.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 1. 로그인한 사용자의 권한을 획득
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		// 2. 권한에 따른 로그인 후의 이동 경로 설정
		String redirectUrl = ""; 
		
		for(GrantedAuthority authority : authorities) {
			
			if(authority.getAuthority().equals("ROLE_DEALER")) { // 딜러 권한
				redirectUrl = "/dealer";
			} else if(authority.getAuthority().equals("ROLE_ADMIN")) { // 관리자 권한
				redirectUrl = "/admin/list";
			} else { // 고객 권한
				redirectUrl = "/main";
			}
 		}
		
		// 3. 설정 경로로 리다이렉션
		response.sendRedirect(request.getContextPath() + redirectUrl);
		
		
	}
}
