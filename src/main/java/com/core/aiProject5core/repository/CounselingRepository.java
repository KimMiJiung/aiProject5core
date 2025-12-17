package com.core.aiProject5core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.core.aiProject5core.entity.Counseling;
import com.core.aiProject5core.entity.Dealer;

@Repository
public interface CounselingRepository extends JpaRepository<Counseling, Long> {

	// 상태별 필터링을 위한 메서드 추가
	Page<Counseling> findByStatus(String status, Pageable pageable);
	Page<Counseling> findByStatusAndCustomerId(String status, Pageable pageable, Long CustomerId);

	// 고객 ID나 연락처를 포함하는 검색을 위한 메서드 추가 (JPA Query Method)
	// Customer 엔티티를 조인하여 Member의 id 또는 phone을 검색
	Page<Counseling> findByCustomer_Member_IdContainingOrCustomer_Member_PhoneContaining(String customerIdKeyword, String phoneKeyword, Pageable pageable);

	// 상태 필터와 검색어를 동시에 적용
	Page<Counseling> findByStatusAndCustomer_Member_IdContainingOrStatusAndCustomer_Member_PhoneContaining(
			String status1, String customerIdKeyword, 
			String status2, String phoneKeyword, 
			Pageable pageable);
	
	Page<Counseling> findByCustomerId(Pageable pageable, Long CustomerId);
	
	List<Counseling> findByCustomerId(Long id);
	
	// 딜러별 상담 조회
	Page<Counseling> findByDealerIdAndStatusNot(Long DealerId, String status, Pageable pageable);
	
	Page<Counseling> findByDealerIdAndStatus(Long DealerId, String status, Pageable pageable);
	
	// 딜러별 상담진행중, 상담완료만 조회
	@Query("SELECT c FROM Counseling c " +
		       "WHERE c.dealer = :dealer " +
		       "AND c.status IN :status " +
		       "ORDER BY c.counselingLikeTime ASC")
	List<Counseling> findByDealerAndStatus(@Param("dealer") Dealer dealer, @Param("status") List<String> status);

	// 딜러별로 상담별 개수 구하기
	long countByDealerAndStatus(Dealer dealer, String status);
    
}

