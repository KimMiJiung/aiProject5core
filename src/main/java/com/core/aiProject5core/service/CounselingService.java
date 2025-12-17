package com.core.aiProject5core.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.aiProject5core.dto.CounselingScheduleDTO;
import com.core.aiProject5core.entity.Counseling;
import com.core.aiProject5core.entity.Dealer;
import com.core.aiProject5core.repository.CounselingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly= true)
public class CounselingService {

	private final CounselingRepository counselingRepository;

	// 전체 조회
	public List<Counseling> findAll() {
		return counselingRepository.findAll();
	}

	// 상담 단건 조회
	public Counseling findById(Long id) {
		return counselingRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상담입니다."));
	}
	
	public List<Counseling> findByCustomerId(Long id) {
		List<Counseling> CounselingList = counselingRepository.findByCustomerId(id);
		return CounselingList;
	}


	@Transactional
	public void deleteCounseling(Long id) {
		counselingRepository.deleteById(id);
	}
	/**
	 * 전체 상담 목록 조회 (페이징 지원)
	 */
	public Page<Counseling> findAll(Pageable pageable) {
		return counselingRepository.findAll(pageable);
	}
	
	/**
	 * 상담 단건 조회 (Optional 반환)
	 * Controller에서 존재 여부를 확인하기 위해 Optional로 반환합니다.
	 */
	public Optional<Counseling> getCounselingById(Long id) {
		return counselingRepository.findById(id);
	}
	

	/**
	 * 상태 및 검색어 기반으로 상담 목록 조회 (페이징 지원)
	 * @param status 상담 상태 (전체일 경우 null 또는 빈 문자열)
	 * @param keyword 검색어 (고객 ID 또는 연락처)
	 * @param pageable 페이징 정보
	 * @return 조건에 맞는 상담 목록 Page
	 */
	public Page<Counseling> findCounselingsByFilter(String status, String keyword, Pageable pageable) {
	    // 1. 상태 필터만 적용 (검색어 없음)
	    if ((keyword == null || keyword.trim().isEmpty()) && (status != null && !status.equals("전체 상태"))) {
	        return counselingRepository.findByStatus(status, pageable);
	    }
	    
	    // 2. 검색어만 적용 (전체 상태)
	    if (status == null || status.equals("전체 상태")) {
	        if (keyword != null && !keyword.trim().isEmpty()) {
	            String searchKeyword = keyword.trim();
	            return counselingRepository.findByCustomer_Member_IdContainingOrCustomer_Member_PhoneContaining(
	                    searchKeyword, searchKeyword, pageable);
	        }
	        // 3. 필터/검색어 모두 없음 (전체 조회)
	        return counselingRepository.findAll(pageable);
	    }
	    
	    // 4. 상태 필터와 검색어 모두 적용
	    if (status != null && !status.equals("전체 상태") && keyword != null && !keyword.trim().isEmpty()) {
	        String searchKeyword = keyword.trim();
	        return counselingRepository.findByStatusAndCustomer_Member_IdContainingOrStatusAndCustomer_Member_PhoneContaining(
	                status, searchKeyword, 
	                status, searchKeyword, 
	                pageable);
	    }
	    
	    // 5. 기본: 전체 조회
	    return counselingRepository.findAll(pageable);
	}
	
	public Page<Counseling> findCounselingsByFilter(String status, String keyword, Pageable pageable, Long customerId) {
	    // 1. 상태 필터만 적용 (검색어 없음)
	    if ((keyword == null || keyword.trim().isEmpty()) && (status != null && !status.equals("전체 상태"))) {
	        return counselingRepository.findByStatusAndCustomerId(status, pageable, customerId);
	    }
	    
	    // 2. 검색어만 적용 (전체 상태)
	    if (status == null || status.equals("전체 상태")) {
	        if (keyword != null && !keyword.trim().isEmpty()) {
	            String searchKeyword = keyword.trim();
	            return counselingRepository.findByCustomer_Member_IdContainingOrCustomer_Member_PhoneContaining(
	                    searchKeyword, searchKeyword, pageable);
	        }
	        // 3. 필터/검색어 모두 없음 (전체 조회)
	        return counselingRepository.findByCustomerId(pageable, customerId);
	    }
	    
	    // 4. 상태 필터와 검색어 모두 적용
	    if (status != null && !status.equals("전체 상태") && keyword != null && !keyword.trim().isEmpty()) {
	        String searchKeyword = keyword.trim();
	        return counselingRepository.findByStatusAndCustomer_Member_IdContainingOrStatusAndCustomer_Member_PhoneContaining(
	                status, searchKeyword, 
	                status, searchKeyword, 
	                pageable);
	    }
	    
	    // 5. 기본: 전체 조회
	    return counselingRepository.findByCustomerId(pageable, customerId);
	}
	
	
	@Transactional
	public Counseling createCounseling(Counseling counseling) {
		return counselingRepository.save(counseling);
	}

	/**
	 * 상담 전체 내용 수정 (applyDetail.html에서 사용)
	 */
	@Transactional
	public Counseling updateCounseling(Long id, Counseling newData) {
		Counseling counseling = findById(id); 
		
		// 필수 필드 업데이트 (Controller에서 넘어온 값이 null이 아닌 경우에만 업데이트)
		if(newData.getStatus() != null) {
		    counseling.setStatus(newData.getStatus());
		}
		if(newData.getVehicleId() != null) {
		    counseling.setVehicleId(newData.getVehicleId());
		}
		if(newData.getCounselingLikeTime() != null) {
		    counseling.setCounselingLikeTime(newData.getCounselingLikeTime());
		}
		if(newData.getPurchasePeriod() != null) {
		    counseling.setPurchasePeriod(newData.getPurchasePeriod());
		}
		if(newData.getPurchasePurpose() != null) {
		    counseling.setPurchasePurpose(newData.getPurchasePurpose());
		}
		if(newData.getOtherInput() != null) {
		    counseling.setOtherInput(newData.getOtherInput());
		}
		// 딜러 정보는 별도의 배정/해제 로직이 있을 수 있으므로 단순 set은 피함.

		// 수정 날짜 업데이트
		counseling.setModifyDate(LocalDateTime.now());
		
		// JPA dirty checking 자동 반영
		return counseling;  
	}
	
	
	// CounselingService.java 파일 내 findAll(Pageable pageable, String status, String keyword, String sort) 메서드
	/**
	 * 전체 상담 목록 조회 (페이징, 필터, 검색 지원)
	 */
	public Page<Counseling> findAll(Pageable pageable, String status, String keyword, String sort) {
	    String searchKeyword = (keyword != null) ? keyword.trim() : null;
	    // status가 null이거나 "전체 상태"인 경우 필터링을 하지 않음
	    boolean isStatusFiltered = (status != null && !status.equals("전체 상태")); 
	    boolean isKeywordUsed = (searchKeyword != null && !searchKeyword.isEmpty());

	    // 1. 상태 필터 + 검색어 적용
	    if (isStatusFiltered && isKeywordUsed) {
	        return counselingRepository.findByStatusAndCustomer_Member_IdContainingOrStatusAndCustomer_Member_PhoneContaining(
	                status, searchKeyword, // status1, customerIdKeyword
	                status, searchKeyword, // status2, phoneKeyword
	                pageable);
	    // 2. 상태 필터만 적용
	    } else if (isStatusFiltered) { 
	        return counselingRepository.findByStatus(status, pageable);
	    // 3. 검색어만 적용
	    } else if (isKeywordUsed) {
	        return counselingRepository.findByCustomer_Member_IdContainingOrCustomer_Member_PhoneContaining(
	                searchKeyword, searchKeyword, pageable);
	    // 4. 필터/검색어 모두 없음 (전체 조회)
	    } else {
	        return counselingRepository.findAll(pageable);
	    }
	}

	/**
	 * 리스트에서 개별 취소 시 상담의 상태만 변경합니다.
	 */
	@Transactional
	public void updateCounselingStatus(Long id, String newStatus) {
	    Counseling counseling = findById(id); 
	    counseling.setStatus(newStatus);
	    counseling.setModifyDate(LocalDateTime.now());
	    counselingRepository.save(counseling); 
	}
	
	/**
	 * 딜러별 상담 조회 (구매완료 제외)
	 */
	public Page<Counseling> findByDealerIdAndStatusNot(Long DealerId, String status, Pageable pageable, String keyword) {
		
		if(keyword.equals("all")) {
			return counselingRepository.findByDealerIdAndStatusNot(DealerId, status, pageable);
		}
		
		return counselingRepository.findByDealerIdAndStatus(DealerId, keyword, pageable);
		
	}
	
	// 딜러별 상담진행중, 상담완료 조회
	public List<CounselingScheduleDTO> findAllCounselingsForCalendar(Dealer dealer, List<String> status) {

	    List<Counseling> counselings = counselingRepository.findByDealerAndStatus(dealer, status);

	    return counselings.stream().map(CounselingScheduleDTO::new).toList();
	    
	}
	
	// 딜러별 상담별 개수 조회
    public long countByDealerAndStatus(Dealer dealer, String status) {
        return counselingRepository.countByDealerAndStatus(dealer, status);
    }
	
	
}