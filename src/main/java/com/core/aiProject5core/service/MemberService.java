package com.core.aiProject5core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.aiProject5core.entity.Member;
import com.core.aiProject5core.entity.Role;
import com.core.aiProject5core.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Optional<Member> _member = memberRepository.findByMemberId(memberId);

		if(_member.isEmpty()) {
			throw new UsernameNotFoundException("회원 ID를 찾을 수 없습니다.");
		}

		Member member = _member.get();

		// 로그인을 할 때 전달받은 회원 ID를 사용하여 비밀번호가 일치하는지를 검사하는 User 객체를 러턴함.
		return User.builder().username(member.getMemberId()).password(member.getPassword())
				.roles(member.getRole().toString()).build();
	}

	// 회원가입
	public void saveMember(Member member) {
		memberRepository.save(member);

	}

	// 회원ID로 회원조회
	public Member findByMemberId(String memberId) {
		return memberRepository.findByMemberId(memberId).get();
	}

	// 회원 수정
	public Member updateMember(Member member) {
		memberRepository.queryUpdateMember(member);
		return memberRepository.findByMemberId(member.getMemberId()).get();
	}

	// 회원 탈퇴
	public void deleteMember(String memberId) {
		Optional<Member> member = memberRepository.findByMemberId(memberId);
		memberRepository.delete(member.get());
	}

	// Role로 회원 조회
	public List<Member> findByRole(Role role) {
		return memberRepository.findByRole(role);
	}

	/**
	 * 멤버 리스트 조회 (페이징, 필터링, 검색)
	 */
	public Page<Member> getMemberList(Pageable pageable, String role, String keyword) {
		Role filterRole = "ALL".equals(role) ? null : Role.valueOf(role);
		String searchKeyword = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();

		return memberRepository.searchAll(filterRole, searchKeyword, pageable);
	}

	/**
	 * 멤버 상태 (Status)만 업데이트
	 */
	@Transactional // 트랜잭션 처리를 위해 필요
	public void updateMemberStatus(Long id, String status) {
		// ID 존재 여부 확인 없이 바로 쿼리 실행
		int updatedCount = memberRepository.queryUpdateStatus(id, status); 

		if (updatedCount == 0) {
			// 업데이트된 행이 0개라면, 해당 ID의 멤버가 존재하지 않음을 의미
			throw new IllegalArgumentException("해당 ID의 멤버를 찾을 수 없거나 업데이트에 실패했습니다: " + id);
		}
	}
}
