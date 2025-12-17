package com.core.aiProject5core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.core.aiProject5core.entity.Customer;
import com.core.aiProject5core.entity.Member;
import com.core.aiProject5core.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	
	public void saveCustomer(Customer customer, Member member) {
		customer.setMember(member);
		customerRepository.save(customer);
	}
	
	public Customer findByMember(Member member) {
		return customerRepository.findByMember(member).get();
	}
	
	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}
	
	
}
