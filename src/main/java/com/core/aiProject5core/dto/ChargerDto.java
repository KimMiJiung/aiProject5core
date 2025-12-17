package com.core.aiProject5core.dto;

import lombok.Data;

@Data
public class ChargerDto {

	private String statId; // 충전소id
	private String statNm; //충전소명
	private String addr;  // 주소
	private String lat;  // 위도
	private String lng;  // 경도
}
