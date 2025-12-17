package com.core.aiProject5core.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeId; 

	@NotBlank(message = "제목은 필수 입력 항목입니다.") // ★ 필수값 추가
	@Column(nullable = false, length = 500)
	private String title; 

	@NotBlank(message = "내용은 필수 입력 항목입니다.") // ★ 필수값 추가
	@Column(nullable = false, length = 500)
	private String content; 

	private String target; 

	@CreatedDate
	private LocalDate createDate; 

	private String createName; 

	private boolean isImportant; 

	public Notice() {
	}

	public Notice(String title, String content, String target, String createName, boolean isImportant) {
		this.title = title;
		this.content = content;
		this.target = target;
		this.createName = createName;
		this.isImportant = isImportant;
		this.createDate = LocalDate.now();
	}
}