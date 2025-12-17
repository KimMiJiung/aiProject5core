package com.core.aiProject5core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 딜러 전용 프로필
 * - 향후 추가될 근무지, 담당지역 등 확장 용도
 */
@Entity
@Data
public class Dealer {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member와 1:1
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
}
