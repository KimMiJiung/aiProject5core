package com.core.aiProject5core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class HopeArea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    /** 구매 상담 희망지역 **/   
    @NotBlank(message = "시도는 필수 입력 사항입니다.")
    @Column(columnDefinition = "varchar(30)")	
    private String sido;
    
    @NotBlank(message = "시군구는 필수 입력 사항입니다.")
    @Column(columnDefinition = "varchar(30)")	
    private String siGunGu;
    
    @NotBlank(message = "읍면동은 필수 입력 사항입니다.")
    @Column(columnDefinition = "varchar(30)")	
    private String eupMyeonDong;
}