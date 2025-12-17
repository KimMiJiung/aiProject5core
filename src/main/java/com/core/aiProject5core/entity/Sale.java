package com.core.aiProject5core.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 판매 엔터티
 * - 상담 완료 → 판매생성
 */
@Entity
@Data
public class Sale {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 판매 일자 
    @Column(columnDefinition = "datetime default now()")
    private LocalDateTime saleDate;

    // 최종 판매 가격 
    private int price;

    // 상담 - 판매완료 -> 고객, 딜러 상담, 차 id 가져옴
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counseling_id")
    private Counseling counseling;

    // 고객 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // 딜러 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;

    // 차량 - 딜러가 선택한 차 id 값이 넘어옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    
}
