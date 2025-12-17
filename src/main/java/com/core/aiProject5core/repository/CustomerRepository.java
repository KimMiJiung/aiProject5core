package com.core.aiProject5core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.core.aiProject5core.entity.Customer;
import com.core.aiProject5core.entity.Member;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Optional<Customer> findByMember(Member member);
	
	
}
