package com.core.aiProject5core.dto;


import lombok.Data;

@Data
public class VehicleOptionView {
	
	// 옵션명
	private String optionName;
	// 옵션의 포함 여부
	private boolean included;
	
	public VehicleOptionView(String optionName, boolean included) {
		this.optionName = optionName;
		this.included = included;
	}
}
