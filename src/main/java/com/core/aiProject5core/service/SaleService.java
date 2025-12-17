package com.core.aiProject5core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.aiProject5core.entity.Dealer;
import com.core.aiProject5core.entity.Sale;
import com.core.aiProject5core.repository.SaleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Sale findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 판매 ID: " + id));
    }

    @Transactional
    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }
    
    
    // 딜러 판매내역 조회
    public Page<Sale> findByDealer(Pageable pageable, Dealer dealer) {
    	return saleRepository.findByDealer(pageable, dealer);
    }
    
    // 회원별 구매내역 조회
    public Page<Sale> findByMemberId(Pageable pagealbe, String memberId) {
    	return saleRepository.findByMemberId(pagealbe, memberId);
    }
    
    
    
    
}
