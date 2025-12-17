package com.core.aiProject5core.repository; // 적절한 패키지명으로 변경하세요

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.aiProject5core.entity.AdminSchedule;

@Repository
public interface AdminScheduleRepository extends JpaRepository<AdminSchedule, Long> {

    // 등록된 일정을 등록 시점(registeredAt) 내림차순으로 가져오는 메서드
    List<AdminSchedule> findAllByOrderByRegisteredAtDesc();
    
    // (선택 사항) 마감일 기준으로 정렬하는 메서드
    List<AdminSchedule> findAllByOrderByDueDateAsc();
}