package com.core.aiProject5core.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.core.aiProject5core.dto.CounselingScheduleDTO;
import com.core.aiProject5core.entity.AdminSchedule;
import com.core.aiProject5core.entity.ApplyStatus;
import com.core.aiProject5core.entity.Counseling;
import com.core.aiProject5core.entity.Dealer;
import com.core.aiProject5core.entity.Member;
import com.core.aiProject5core.entity.Notice;
import com.core.aiProject5core.entity.Role;
import com.core.aiProject5core.entity.Sale;
import com.core.aiProject5core.service.AdminScheduleService;
import com.core.aiProject5core.service.CounselingService;
import com.core.aiProject5core.service.CustomerService;
import com.core.aiProject5core.service.DealerService;
import com.core.aiProject5core.service.MemberService;
import com.core.aiProject5core.service.NoticeService;
import com.core.aiProject5core.service.SaleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@Controller
//@RequestMapping("/main")
public class MainController {


	private final MemberService memberService;
	private final CustomerService customerService;
	private final DealerService dealerService;
	private final CounselingService counselingService;
	private final SaleService saleService;
	private final NoticeService noticeService;
	private final AdminScheduleService adminScheduleService;


	@GetMapping("/main")
	public String requestCarList(Model model) {
		return "main";
	}

	// 고객센터 페이지
	@GetMapping("/support")
	public String requestCustomerService() {
		return "customer"; 
	}





	/*
	 * -------------------------------------------------------------------------------------------
	 * DEALER
	 * -------------------------------------------------------------------------------------------
	 */
	// 딜러(Role.DEALER)
	@GetMapping("/dealer")
	public String requestDealerMain() {

		return "redirect:/dealer/profile";
	}

	@GetMapping("/dealer/profile")
	public String requestDealerProfile(Principal principal,
			@RequestParam(value="page", defaultValue="0") int page,
			Model model) {
		Member member = memberService.findByMemberId(principal.getName());
		Dealer dealer = dealerService.findByMember(member);
		
		Sort sortObj = Sort.by("createDate").ascending();
		Pageable pageable = PageRequest.of(page, 10, sortObj); 

		List<Member> customerList = memberService.findByRole(Role.CUSTOMER);

		Page<Counseling> counselingList = counselingService.findCounselingsByFilter(ApplyStatus.COUNSELING_HODDING.getStatusName(), null, pageable);

	    // 전체 상담 일정
	    List<CounselingScheduleDTO> counselingSchedule =
	            counselingService.findAllCounselingsForCalendar(dealer, List.of("상담진행중", "상담완료"));
	    
	    long progressCnt = counselingService.countByDealerAndStatus(dealer, "상담진행중");
	    long completCnt   = counselingService.countByDealerAndStatus(dealer, "상담완료");
	    long saleCnt        = counselingService.countByDealerAndStatus(dealer, "구매완료");
	    
	    // 상담진행중, 상담완료, 구매완료 카운트
	    model.addAttribute("progressCnt", progressCnt);
	    model.addAttribute("completCnt", completCnt);
	    model.addAttribute("saleCnt", saleCnt);
	    
	    // 스트림잇이 읽을 dealerId를 저장
	    model.addAttribute("dealerId", dealer.getId());
	    
		model.addAttribute("counselingList", counselingList);
		model.addAttribute("customerList", customerList);
		model.addAttribute("dealer", member);
		model.addAttribute("counselingSchedule", counselingSchedule != null ? counselingSchedule : List.of());
		
		model.addAttribute("currentPage", page);
		
		// 공지사항, size가 8로 설정
		Pageable pageableNotice = PageRequest.of(page, 8, sortObj);  
        Page<Notice> noticePage = noticeService.findAllNotices(pageableNotice);
        
        model.addAttribute("noticePage", noticePage);
        model.addAttribute("notices", noticePage.getContent());
        

		return "dealer/profile";
	}

	// 딜러
	@GetMapping("/dealer/care")
	public String requestDealercareList(@RequestParam(value="page", defaultValue="0") int page,
			Model model) {

		Sort sortObj = Sort.by("createDate").ascending();
		Pageable pageable = PageRequest.of(page, 10, sortObj);  
		Page<Counseling> counselingList = counselingService.findCounselingsByFilter(ApplyStatus.COUNSELING_HODDING.getStatusName(), null, pageable);

		model.addAttribute("counselingList", counselingList);

		return "dealer/careList";
	}

	// 딜러가 상담 선택
	@GetMapping("/care/choice/{id}/{status}/{counselingLikeTime}")
	public String requestUpdateStatus(Principal principal,
			@PathVariable("id") Long id,
			@PathVariable("status") String status,
			@PathVariable("counselingLikeTime") String counselingLikeTime,
			Model model) {

		Member member = memberService.findByMemberId(principal.getName());
		Dealer dealer = dealerService.findByMember(member);

		if(ApplyStatus.COUNSELING_HODDING.getStatusName().equals(status)) {
			return "redirect:/dealer/care";
		}else if(ApplyStatus.COUNSELING_PROGRESS.getStatusName().equals(status)) {
			status = ApplyStatus.COUNSELING_PROGRESS.getStatusName();
		}

		Counseling counseling = counselingService.findById(id);
		counseling.setDealer(dealer);
		counseling.setStatus(status);

		counseling.setCounselingLikeTime(counselingLikeTime);

		counselingService.createCounseling(counseling);

		return "redirect:/dealer/myCustomer";
	}

	// 딜러의 상담고객
	@GetMapping("/dealer/myCustomer")
	public String requestMyCustomer(@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="keyword", required=false, defaultValue="all") String keyword,
			Principal principal,
			Model model) {

		Sort sortObj = Sort.by("status").ascending();
		Pageable pageable = PageRequest.of(page, 10, sortObj);  


		Member member = memberService.findByMemberId(principal.getName());
		Dealer dealer = dealerService.findByMember(member);
		Page<Counseling> counselingList = counselingService.findByDealerIdAndStatusNot(dealer.getId(), "구매완료", pageable, keyword);

		model.addAttribute("counselingList", counselingList);
		model.addAttribute("keyword", keyword);  // 버튼 상태용

		return "dealer/myCustomerList";
	}

	// 딜러의 상세 상담고객
	@GetMapping("/dealer/myCustomer/{id}")
	public String requestMyCustomerDetail(@PathVariable("id") Long id,
			Model model) {

		Counseling counseling = counselingService.findById(id);

		model.addAttribute("counseling", counseling);

		return "dealer/myCustomerDetail";
	}
	
	// 딜러 판매리스트
	@GetMapping("/dealer/sale")
	public String requestSaleList(Principal principal,
			@RequestParam(value="page", defaultValue="0") int page,
			Model model) {

		Member member = memberService.findByMemberId(principal.getName());
		Dealer dealer = dealerService.findByMember(member);
		
		Sort sortObj = Sort.by("id").descending();
		Pageable pageable = PageRequest.of(page, 10, sortObj);  
		Page<Sale> paging = saleService.findByDealer(pageable, dealer);

		model.addAttribute("saleList", paging);

		return "dealer/saleList";
	}

	/*
	 * -------------------------------------------------------------------------------------------
	 * ADMIN
	 * -------------------------------------------------------------------------------------------
	 */
	// 관리자(Role.ADMIN)		
	@GetMapping("/admin")
	public String requestAdminMain() {

		return "redirect:/admin/profile";
	}

	// 관리자 프로필
	@GetMapping("/admin/profile")
	public String requestAdminProfile(Principal principal,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value = "size", defaultValue = "8") int size, 
			Model model) {

		if (!model.containsAttribute("notice")) {
            model.addAttribute("notice", new Notice());
        }
		Member admin = memberService.findByMemberId(principal.getName());
		List<Member> customerList = memberService.findByRole(Role.ADMIN);

		Sort sortObj = Sort.by("createDate").ascending();
		// 한페이지에 8건씩
		Pageable pageable = PageRequest.of(page, 8, sortObj);  
        Page<Notice> noticePage = noticeService.findAllNotices(pageable);
        
        // 스케줄 목록 조회
        List<AdminSchedule> schedules = adminScheduleService.findAllSchedules();
        
        model.addAttribute("schedules", schedules);
        model.addAttribute("noticePage", noticePage);
        model.addAttribute("notices", noticePage.getContent());
        model.addAttribute("currentPage", page);
		model.addAttribute("customerList", customerList);
		model.addAttribute("admin", admin);

		return "admin/profile";
	}

	// 관리자 멤버리스트
	@GetMapping("/admin/list")
	public String list(
			Model model,
			@RequestParam(value = "role", defaultValue = "ALL") String role,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			// 기본 정렬: PK ID 오름차순
			@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

		Page<Member> memberPage = memberService.getMemberList(pageable, role, keyword);

		// 페이지 블록 계산 (5개 블록)
		int startPage = Math.max(0, memberPage.getNumber() - 2);
		int endPage = Math.min(memberPage.getTotalPages() - 1, memberPage.getNumber() + 2);

		model.addAttribute("memberPage", memberPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("roles", Role.values());

		model.addAttribute("currentRole", role);
		model.addAttribute("currentKeyword", keyword);

		Sort.Order order = pageable.getSort().iterator().next();
		String sortProperty = order.getProperty();
		String sortDirection = order.getDirection().toString().toLowerCase(); // 'asc' 또는 'desc'

		model.addAttribute("currentSort", String.format("%s,%s", sortProperty, sortDirection));
		return "admin/list";
	}

	// 관리자 멤버 상태 업데이트
	@PostMapping("/admin/update-status")
	public String updateMemberStatus(@RequestParam("id") Long id, 
			@RequestParam("status") String status,
			RedirectAttributes redirectAttributes) { 
		try {
			memberService.updateMemberStatus(id, status); 

			redirectAttributes.addFlashAttribute("successMessage", 
					String.format("PK ID [%d]의 상태가 '%s'로 성공적으로 수정되었습니다.", id, status));
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		} catch (DataAccessException e) { 
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "데이터베이스 오류: 상태를 저장하는 데 실패했습니다. (값 길이 초과, 형식 오류 등) 관리자에게 문의하세요.");
		} catch (Exception e) {
			e.printStackTrace(); 
			redirectAttributes.addFlashAttribute("errorMessage", "알 수 없는 오류: 업데이트 중 치명적인 오류가 발생했습니다.");
		}
		return "redirect:/admin/list";
	}
	
	//전체 알림/공지 등록
	@PostMapping("/admin/notice/add")
	public String registerNotice(
			@Valid @ModelAttribute Notice notice,
			BindingResult bindingResult, 
			RedirectAttributes rt
			) {
		// 1. 유효성 검사 실패 (필수 값 누락 시)
		if (bindingResult.hasErrors()) {
			rt.addFlashAttribute("org.springframework.validation.BindingResult.notice", bindingResult);
			rt.addFlashAttribute("notice", notice);
			return "redirect:/admin/profile";
		}

		try {
			Notice savedNotice = noticeService.registerNewNotice(
					notice.getTitle(), 
					notice.getContent(), 
					notice.getTarget(), 
					Role.ADMIN.toString() // 등록자는 Role.ADMIN으로 고정
					);

			// 3. 성공 메시지 전송
			rt.addFlashAttribute("successMessage", "공지사항이 성공적으로 등록되었습니다: " + savedNotice.getTitle());

		} catch (Exception e) {
			// 4. 시스템 오류 처리
			System.err.println("공지 등록 중 시스템 오류 발생: " + e.getMessage());
			rt.addFlashAttribute("errorMessage", "공지 등록 중 시스템 오류가 발생했습니다.");
		}
		return "redirect:/admin/profile"; 
	}
	
	// 관리자 글로벌 판매 실적	
	@GetMapping("/admin/globalSales")
	public String requestGlobalSales() {
		return "admin/globalSales";
	}
	
	// 관리자 AI수요예측	
	@GetMapping("/admin/aiForecast")
	public String requestAiForecast() {
		return "admin/aiForecast";
	}
	
	// 관리자 차량별 판매 실적	
	@GetMapping("/admin/modelSales")
	public String requestModelSales() {
		return "admin/modelSales";
	}
	
	// 관리자 스케쥴 등록
	@PostMapping("/admin/schedule/add")
	public String registerSchedule(
	    @RequestParam("scheduleText") String content, // 메모 내용
	    @RequestParam(value = "scheduleDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate, // 마감일 (선택 사항)
	    RedirectAttributes rt
	) {
	    try {
	        adminScheduleService.registerSchedule(content, dueDate);
	        rt.addFlashAttribute("successMessage", "새로운 업무 스케줄이 등록되었습니다.");
	    } catch (IllegalArgumentException e) {
	        rt.addFlashAttribute("errorMessage", e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        rt.addFlashAttribute("errorMessage", "스케줄 등록 중 오류가 발생했습니다.");
	    }	    
	    return "redirect:/admin/profile";
	}	
	
	// 관리자 스케쥴 삭제
	@PostMapping("/admin/schedule/delete/{id}")
	public String deleteSchedule(
	    @PathVariable("id") Long scheduleId,
	    RedirectAttributes rt
	) {
	    try {
	        adminScheduleService.deleteSchedule(scheduleId);
	        rt.addFlashAttribute("successMessage", "스케줄이 성공적으로 삭제되었습니다.");
	    } catch (IllegalArgumentException e) {
	        rt.addFlashAttribute("errorMessage", e.getMessage());
	    } catch (Exception e) {
	        rt.addFlashAttribute("errorMessage", "스케줄 삭제 중 오류가 발생했습니다.");
	    }
	    return "redirect:/admin/profile";
	}	
}
