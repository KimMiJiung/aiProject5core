package com.core.aiProject5core.entity;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 회원 엔터티
 * 고객/딜러 모두 공통적으로 사용
 */
@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 고객/딜러 역할 
    @Enumerated(EnumType.STRING)
    private Role role;

    // 로그인 ID
    @Column(unique = true)
    @NotBlank(message = "회원ID는 필수 입력사항입니다.")
    private String memberId;

    // 비밀번호 
    @Column(nullable = false)
    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    private String password;
    
	// 테이블에서 생성 제외
	@Transient
	@NotBlank(message = "비밀번호 확인은 필수 입력사항입니다.")
	private String password2;

    // 이름 
	@NotBlank(message = "이름은 필수 입력사항입니다.")
    private String name;

    // 성별 
    private String gender;

    // 연락처 
    @NotBlank(message = "연락처는 필수 입력사항입니다.")
    private String phone;

    // 이메일 
    @NotBlank(message = "이메일은 필수 입력사항입니다.")
    private String email;

    // 가입 날짜 
    @Column(columnDefinition = "datetime default now()")
    private LocalDateTime joinDate;
    
    // customer에서 외래키를 가짐
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    private Customer customer;

    // dealer에서 외래키를 가짐
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    private Dealer dealer;
    
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	@Valid
	private Address address;
	
	// 테이블에서 생성 제외
	/*
	 * 01: 고객, 02: 딜러
	 */
	@Transient
	private String roleCheck;
    
	// 회원 생성 메서드
	// - 비밀번호 암호화, 사용자역할 설정(True는 일반 사용자)	
	public static Member createMember(Member member, PasswordEncoder passwordEncoder, String roleCheck) {
		member.password = passwordEncoder.encode(member.password);
		if(roleCheck.equals("01")) member.role = Role.CUSTOMER;
		else if(roleCheck.equals("02")) member.role = Role.DEALER;
		else if(roleCheck.equals("03")) member.role = Role.ADMIN;
		return member;
	}
	
	@Column(columnDefinition = "varchar(20) default 'ACTIVE'")
	private String status = "ACTIVE";
}
