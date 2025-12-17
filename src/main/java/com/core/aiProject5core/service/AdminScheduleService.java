package com.core.aiProject5core.service; // 적절한 패키지명으로 변경하세요

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.aiProject5core.entity.AdminSchedule;
import com.core.aiProject5core.repository.AdminScheduleRepository;

@Service
@Transactional
public class AdminScheduleService {

    @Autowired
    private AdminScheduleRepository adminScheduleRepository;

    /**
     * 새로운 스케줄을 등록합니다.
     */
    public AdminSchedule registerSchedule(String content, LocalDate dueDate) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("스케줄 내용은 필수 항목입니다.");
        }
        
        AdminSchedule schedule = new AdminSchedule();
        schedule.setContent(content);
        schedule.setDueDate(dueDate);
        
        return adminScheduleRepository.save(schedule);
    }

    /**
     * 모든 스케줄을 등록일 내림차순(최신순)으로 조회합니다.
     */
    @Transactional(readOnly = true)
    public List<AdminSchedule> findAllSchedules() {
        return adminScheduleRepository.findAllByOrderByRegisteredAtDesc();
    }
    
    /**
     * 특정 ID의 스케줄을 삭제합니다.
     */
    public void deleteSchedule(Long scheduleId) {
        if (adminScheduleRepository.existsById(scheduleId)) {
             adminScheduleRepository.deleteById(scheduleId);
        } else {
             // 삭제할 항목이 없어도 오류를 발생시키지 않도록 처리 가능
             throw new IllegalArgumentException("해당 ID의 스케줄이 존재하지 않습니다.");
        }
    }
}