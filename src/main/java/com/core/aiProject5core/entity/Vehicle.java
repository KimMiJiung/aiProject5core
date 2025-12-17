package com.core.aiProject5core.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 차량 기본 정보
 */
@Entity
@Getter
@Setter
public class Vehicle {
	
	// auto-increment로 생성되는 고유id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 모델 코드 - 차를 구분하기 위한 코드 (예: avante, avante-N 등)
    @Column(columnDefinition = "varchar(20)")
    private String modelCode;

    // 차량 이름 (예: 아반떼, EV6 등) 
    @NotBlank(message = "차량명은 필수 입력 사항입니다.")
	@Column(columnDefinition = "varchar(100)")
    private String name;

    // 브랜드 (현대만)
    @Column(columnDefinition = "varchar(30)")
    private String brand;

    // 연료 타입
    @Column(columnDefinition = "varchar(30)")
    private String fuelType; // 휘발유, 경유, 전기 3종

    // 연식 
    @Column(columnDefinition = "varchar(10)")
    private String year;

    // 배기량 
    private Integer displacement;

    // 차 상태 (SUV, 세단 등) 
    @Column(columnDefinition = "varchar(30)")
    private String vehicleType;
    
    // 기본 가격 - 초기값 
	// 최소값 0, 음수는 사용불가, 전체 11자리, 소수점 2자리
	@NotNull @Min(value=0) @Digits(integer=11, fraction=2)
    private int basePrice;
	
	// 최종 가격
	// 기본 가격에 트림 별 가격이 계속 누적되므로, 기본 가격 + 트림 가격 = 최종 가격으로 해결 
	@NotNull @Min(value=0) @Digits(integer=11, fraction=2)
	private int finalPrice = 0;
    
	@Column(columnDefinition = "varchar(30) default 'no_image.jpg'")
	private String fileName;  // 차량이미지의 파일명
	
	@Transient
	private MultipartFile vehicleImage; // 업로드된 차량 이미지
	
	// 연비
	private Double fuelEfficiency;
	
	
    // 차량 옵션 / 컬러
    // - 차량 상담 시 그 차량에 대해서 선택한 옵션들이 리스트로 한번에 저장되게끔
	// - 차량 데이터 추가 시 옵션과 컬러 데이터를 한번에 저장
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<VehicleOption> options = new ArrayList<>();
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<VehicleColor> colors = new ArrayList<>();
    
    
    
    /*
    # 트림 별 가격 (사용할지 고민중)
    - 3가지 트림으로 가격 차별화
    - 모든 옵션에 가격을 매기는 것이 어렵고, 많은 차의 데이터에 가격을 각각 부여해야 하는 부담이 있음
    - 트림에 따라 차의 기본 가격에서 +가 되는 방식을 사용 (계산은 service에서 처리)
    -- ex) 스탠다드: + 0원, 익스클루시브: + 200만원, 프레스티지: + 400만원
    */
    @Column(columnDefinition = "varchar(20)")
    private String trim; // Standard, Exclusive, Prestige (대소문자 구분 없음 -> 서비스에서 처리)


    
}
