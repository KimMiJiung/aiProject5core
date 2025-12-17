package com.core.aiProject5core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 고객 전용 프로필
 * - 구매횟수, 세그먼트(VIP/일반)
 */
@Entity
@Data
public class Customer {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구매 횟수 
    private int purchaseCount = 0;

    // VIP / 일반 
    @Column(columnDefinition = "varchar(20) default '일반'")
    private String segment;

    // Member와 1:1 
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // VIP 여부 계산 
    public boolean isVip() {
        return purchaseCount >= 3;
    }
    
}
