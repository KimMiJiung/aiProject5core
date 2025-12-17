package com.core.aiProject5core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 차량 옵션 정보
 */
@Entity
@Data
public class VehicleOption {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 옵션명 - 화면에 표시되는 한글 옵션명
    private String optionName;
    
    // 옵션코드 - DB에서 처리하고 파일 이름을 위한 영문 코드
    private String optionCode;
    
    // 차량 참조 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    
    // 트림
    // - 트림 별 옵션을 차등 적용하기 위해 어떤 트림에 어떤 옵션들이 저장되는지 구분
    @Column(columnDefinition = "varchar(20)")
    private String trimLevel;
    
    
}
