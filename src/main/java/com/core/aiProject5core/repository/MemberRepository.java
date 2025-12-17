package com.core.aiProject5core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.core.aiProject5core.entity.Member;
import com.core.aiProject5core.entity.Role;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Optional<Member> findByMemberId(String memberId);
	
	List<Member> findByRole(Role role);
	
	// 회원정보 수정
	public final String UPDATE_MEMBER = "update member m inner join address a on m.address_id = a.id "
			+ " set password = :#{#member.password}, phone = :#{#member.phone}, email = :#{#member.email}, "
			+ " gender = :#{#member.gender}, country = :#{#member.address.country}, zipcode = :#{#member.address.zipcode}, "
			+ " basic_address = :#{#member.address.basicAddress}, detail_address = :#{#member.address.detailAddress} "
			+ " where member_id = :#{#member.memberId}";
	@Transactional
	@Modifying
	@Query(value = UPDATE_MEMBER, nativeQuery = true)
	void queryUpdateMember(@Param("member") Member member);
	
	/**
     * 역할(Role) 및 키워드 검색을 포함하는 페이징 쿼리
     */
    @Query("SELECT m FROM Member m WHERE " +
           // 역할 필터링
           "(:role IS NULL OR m.role = :role) AND " +
           // 키워드 검색
           "(:keyword IS NULL OR :keyword = '' OR " +
           
           // **********************************************
           // [핵심 수정] PK 'm.id' (Long) 대신 로그인 ID 'm.memberId' (String) 사용
           "  LOWER(m.memberId) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " + 
           // **********************************************
           
           "  LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "  LOWER(m.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Member> searchAll(
            @Param("role") Role role,
            @Param("keyword") String keyword,
            Pageable pageable);
            
    Optional<Member> findById(Long id);

	public final String UPDATE_MEMBER_STATUS = "update member set status = :status where id = :id";
	@Transactional
	@Modifying
	@Query(value = UPDATE_MEMBER_STATUS, nativeQuery = true)
    int queryUpdateStatus(@Param("id") Long id, @Param("status") String status);
	
}
