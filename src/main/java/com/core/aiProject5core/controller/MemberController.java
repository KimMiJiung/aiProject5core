package com.core.aiProject5core.controller;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.aiProject5core.entity.Admin;
import com.core.aiProject5core.entity.Customer;
import com.core.aiProject5core.entity.Dealer;
import com.core.aiProject5core.entity.Member;
import com.core.aiProject5core.entity.Role;
import com.core.aiProject5core.service.AdminService;
import com.core.aiProject5core.service.CustomerService;
import com.core.aiProject5core.service.DealerService;
import com.core.aiProject5core.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;
	private final CustomerService customerService;
	private final DealerService dealerService;
	private final AdminService adminService;
	
	private final PasswordEncoder passwordEncoder;
	
	// [1] 일반 사용자
	// 회원가입 폼
	@GetMapping("/add/{roleCheck}")
	public String addMemberForm(Model model, @PathVariable("roleCheck") String roleCheck) {
		Member member = new Member();
		member.setRoleCheck(roleCheck);
		model.addAttribute("member", member);
		return "member/addMember";
	}
	
	// 회원가입 처리
	@PostMapping("/add")
	public String addMemberPro(@Valid @ModelAttribute Member member, 
			BindingResult bindingResult, 
			@Valid @ModelAttribute Customer customer,
			@Valid @ModelAttribute Dealer dealer,
			@Valid @ModelAttribute Admin admin,
			Model model) {
		// 유효성 검사
		if(bindingResult.hasErrors()) {
			return "member/addMember";
		}
		
		// 입력한 비밀번호 2개가 일치하는지를 검사
		if(!member.getPassword().equals(member.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordIncorrect", "입력한 비밀번호가 일치하지 않습니다.");
			return "member/addMember";
		}
		
		// 중복 ID 체크
		// DataIntegrityViolationException -> unique 속성을 위배했을 때 발생하는 예외
		try {
			
			Member m = Member.createMember(member, passwordEncoder, member.getRoleCheck());
			m.setJoinDate(LocalDateTime.now());
			memberService.saveMember(m);
			
			if(member.getRoleCheck().equals("01")) {
				customer.setSegment("일반");
				customer.setPurchaseCount(0);
				customerService.saveCustomer(customer, member);
			} else if(member.getRoleCheck().equals("02")) {
				dealerService.saveDealer(dealer, member);
			}  else if(member.getRoleCheck().equals("03")) {
				adminService.saveAdmin(admin, member);
			} 				
		} catch(DataIntegrityViolationException e) {
			// rejectValue(필드명, 오류코드, 메시지)
			bindingResult.rejectValue("memberId", "duplecatedMemberId", "이미 존재하는 회원 ID입니다.");
			return "member/addMember";
		} catch(Exception e) {
			bindingResult.rejectValue("memberId", "duplecatedMemberId", e.getMessage());
			return "member/addMember";
		}

		return "redirect:/main";
	}
	
	// 회원정보 조회
	@GetMapping("/update/{memberId}")
	public String updateCustomerForm(@PathVariable("memberId") String memberId, Model model) {
		Member member = memberService.findByMemberId(memberId);
		model.addAttribute("member", member);
		
		return "member/updateMember";
	}
	
	// 회원정보 수정 처리
	@PostMapping("/update")
	public String updateMemberPro(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
		// 유효성 검사
		if(bindingResult.hasErrors()) {
			return "member/updateMember";
		}
		
		// 입력한 비밀번호 2개가 일치하는지를 검사
		if(!member.getPassword().equals(member.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordIncorrect", "입력한 비밀번호가 일치하지 않습니다.");
			return "member/updateMember";
		}
		
		
		// 고객은 01, 딜러는 02
		if(member.getRole() == Role.CUSTOMER) member.setRoleCheck("01");
		else if(member.getRole() == Role.DEALER) member.setRoleCheck("02");
		
		// 수정 처리
		try {
			Member m = Member.createMember(member, passwordEncoder, member.getRoleCheck());
			memberService.updateMember(m);
		} catch(Exception e) {
			e.printStackTrace();
			return "member/updateMember";
		}
		
		return "redirect:/member/update/" + member.getMemberId();
	}
	
	
	// 회원삭제(탈퇴)
	@GetMapping("/delete/{memberId}")
	public String deleteMemberPro(@PathVariable("memberId") String memberId) {
		memberService.deleteMember(memberId);
		
		return "redirect:/logout";
	}
	
	
}