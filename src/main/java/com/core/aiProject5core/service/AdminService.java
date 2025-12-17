package com.core.aiProject5core.service;

import org.springframework.stereotype.Service;

import com.core.aiProject5core.entity.Admin;
import com.core.aiProject5core.entity.Member;
import com.core.aiProject5core.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {
	private final AdminRepository adminRepository;
	
	public void saveAdmin(Admin admin, Member member) {
		admin.setMember(member);
		adminRepository.save(admin);
	}
	
	public Admin findByMember(Member member) {
		return adminRepository.findByMember(member).get();
	}
}
