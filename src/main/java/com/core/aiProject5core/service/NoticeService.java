package com.core.aiProject5core.service;

import java.util.List;

import org.springframework.data.domain.Page; // Spring Data Page 인터페이스 추가
import org.springframework.data.domain.Pageable; // Spring Data Pageable 인터페이스 추가
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.aiProject5core.entity.Notice;
import com.core.aiProject5core.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /**
     * 공지사항을 신규등록
     * @param title 공지 제목
     * @param content 공지 내용
     * @param target 전송 대상
     * @param Role.ADMIN 등록자
     * @return 저장된 Notice 엔티티
     */
    @Transactional
    public Notice registerNewNotice(String title, String content, String target, String registrant) {
        
        Notice newNotice = new Notice(title, content, target, registrant, false);       
        return noticeRepository.save(newNotice);
    }

	public List<Notice> findAll() {
		return noticeRepository.findAll();
	}
	
	/**
     * 모든 공지사항을 페이징하여 조회 (최신 등록일 순)
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬 등)
     * @return 페이징 처리된 Notice 엔티티 목록 (Page 객체)
     */
	@Transactional(readOnly = true)
    public Page<Notice> findAllNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }
}