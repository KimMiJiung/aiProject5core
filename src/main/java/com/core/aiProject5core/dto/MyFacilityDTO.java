package com.core.aiProject5core.dto;

import lombok.Data;

@Data
public class MyFacilityDTO {
	private String hp_name; // 병원명
	private String hp_sido; //시도
	private String hp_gugun; // 구군
	private String hp_addr; // 주소
	private String hp_url;  // 참조URL
	private String hp_latitude;
	private String hp_longitude;
	private String disKM; // 거리
	private String rNum;
}
