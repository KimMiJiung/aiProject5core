package com.core.aiProject5core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.core.aiProject5core.entity.Dealer;
import com.core.aiProject5core.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	List<Sale> findAll();
	
	// 회원 별 구매내역 조회
	@Query("""
		       SELECT s
		       FROM Sale s
		       JOIN s.customer c
		       JOIN c.member m
		       WHERE m.memberId = :memberId
		       """)
	Page<Sale> findByMemberId(Pageable pageable, @Param("memberId") String memberId);
	
	
	List<Sale> findByDealer(Dealer dealer);
	// dealer엔터티에 해당하는 sale 찾기
	Page<Sale> findByDealer(Pageable pageable, Dealer dealer);
	
	
	// 딜러별 월판매 그래프
	@Query(value = "SELECT " +
	       "DATE_FORMAT(s.sale_date, '%b') as monthName, " +
	       "COUNT(s.id) as salesCount, " +
	       "SUM(s.price) as totalPrice " +
	       "FROM sale s " +
	       "WHERE YEAR(s.sale_date) = :year AND s.dealer_id = :dealerId " +
	       "GROUP BY MONTH(s.sale_date) " +
	       "ORDER BY MONTH(s.sale_date)", nativeQuery = true)
	List<Object[]> findMonthlySalesByDealer(@Param("year") int year, @Param("dealerId") Long dealerId);

	
}
