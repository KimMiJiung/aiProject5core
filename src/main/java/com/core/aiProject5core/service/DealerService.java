package com.core.aiProject5core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.core.aiProject5core.entity.Dealer;
import com.core.aiProject5core.entity.Member;
import com.core.aiProject5core.repository.DealerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DealerService {
	
	private final DealerRepository dealerRepository;
	
	public void saveDealer(Dealer dealer, Member member) {
		dealer.setMember(member);
		dealerRepository.save(dealer);
	}
	
	public Dealer findByMember(Member member) {
		return dealerRepository.findByMember(member).get();
	}
	
	public Optional<Dealer> findById(Long id) {
		return dealerRepository.findById(id);
	}
}
