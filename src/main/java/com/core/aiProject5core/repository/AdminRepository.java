package com.core.aiProject5core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.aiProject5core.entity.Admin;
import com.core.aiProject5core.entity.Member;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
	Optional<Admin> findByMember(Member member);
}
