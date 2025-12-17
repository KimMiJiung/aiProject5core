package com.core.aiProject5core.entity; // 적절한 패키지명으로 변경하세요

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AdminSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 스케줄 고유 ID

    @Column(nullable = false, length = 500)
    private String content; // 스케줄/메모 내용

    @Column(name = "due_date")
    private LocalDate dueDate; // 마감일

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt; // 등록 시점

    public AdminSchedule() {
        this.registeredAt = LocalDateTime.now(); // 객체 생성 시 현재 시간으로 자동 설정
    }
}