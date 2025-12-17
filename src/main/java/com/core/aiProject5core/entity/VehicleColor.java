package com.core.aiProject5core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 차량 색상 정보
 */
@Entity
@Data
public class VehicleColor {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 색상명 
    @Column(columnDefinition = "varchar(30)")
    private String color;

    // 색상 이미지 URL
    @Column(columnDefinition = "varchar(255) default 'no_image.jpg'")
    private String imageColorUrl;

    // 차량 참조 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    
}
