package com.core.aiProject5core.entity;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 상담 엔터티
 * - 고객이 신청 → 딜러가 상태 변경
 */  
@Entity
@Data
public class Counseling {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "차량명는 필수 입력 사항입니다.")
	@Column(columnDefinition = "varchar(30)")
	private Long vehicleId;
	
	@Column(columnDefinition = "varchar(30)")
	private String vehicleName;
    
	/** 구매 상담 희망지역 **/   
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hopearea_id")
	@Valid
	private HopeArea hopearea;

	/** 구매 희망 정보 **/
	// 차량 구매 목적
	@NotBlank(message = "차량 구매 목적은 필수 선택 사항입니다.")
	@Column(columnDefinition = "varchar(100)")	
	private String purchasePurpose;

	@Column(columnDefinition = "varchar(100)")	
	private String otherInput;   // ‘기타’ 입력 시

	// 희망 구매 시점
	@NotBlank(message = "희망 구매 시점은 필수 선택 사항입니다.")
	@Column(columnDefinition = "varchar(100)")	
	private String purchasePeriod;

	// 선호 차량 유형
	@Column(columnDefinition = "varchar(100)")	
	private String vehicleType;

	// 선호 엔진
	@Column(columnDefinition = "varchar(100)")	
	private String engineType;

	// 상담 희망 시간
	@Column(columnDefinition = "varchar(30)")
	private String counselingLikeTime;

	// 상담 상태 (상담신청/상담완료/판매완료) 
	@Column(columnDefinition = "varchar(20) default '상담신청'")
	private String status;

	// 상담 신청 고객 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	// 담당 딜러 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dealer_id")
	private Dealer dealer;

	// 추가분
	// 등록 날짜와 시간
	@CreatedDate
	private LocalDateTime createDate;
	
	// 수정 날짜와 시간
	private LocalDateTime modifyDate;
	
	
	/*
	- AI 추천 저장
	- json형식으로 넘어오는 내용을 문자열로 바로 저장 
	*/
	@Column(columnDefinition = "TEXT")
	private String aiRecommendJson;    // 추천 후보
	
	@Column(columnDefinition = "TEXT")
	private String finalRecommendJson; // 최종 추천
	
	
	
	
	
	
	
	
	

}