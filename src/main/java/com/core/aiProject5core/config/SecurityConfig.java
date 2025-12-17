package com.core.aiProject5core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// 비밀번호 암호화
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler;
	
	// 1. ADMIN 역할을 가진 사용자만 /admin로 접근, DEALER 역할을 가진 사용자만 /dealer로 접근 가능
	// 2. 로그인 기본 경로, 성공했을 때의 경로, 실패했을 때의 경로, 로그인 처리에 사용되는 파라미터명(name으로 전달되는 이름) 설정 
	// 3. 로그아웃 기본 경로, 성공했을 때의 경로
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			// 사용자 권한에 따른 접근 권한 설정
			.authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.requestMatchers("/admin/**").hasRole("ADMIN")		// ADMIN 권한
				.requestMatchers("/dealer/**").hasRole("DEALER")	// 딜러 권한
				.anyRequest().permitAll())
			
			// 로그인 처리
			.formLogin(formLogin -> formLogin
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.successHandler(customLoginSuccessHandler)
				.failureUrl("/loginfailed")
				.usernameParameter("username")
				.passwordParameter("password"))
			// 로그아웃 처리
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/main"));
		
		return http.build();
	}
	
	// 스프링 시큐리티가 회원 인증시에 인증과 권한을 부여하는 프로세스를 처리하는 메서드
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
