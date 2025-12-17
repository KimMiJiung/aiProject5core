package com.core.aiProject5core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.aiProject5core.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
