package com.core.aiProject5core.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.aiProject5core.entity.Counseling;
import com.core.aiProject5core.entity.Customer;
import com.core.aiProject5core.entity.Dealer;
import com.core.aiProject5core.entity.Sale;
import com.core.aiProject5core.entity.Vehicle;
import com.core.aiProject5core.service.CounselingService;
import com.core.aiProject5core.service.CustomerService;
import com.core.aiProject5core.service.DealerService;
import com.core.aiProject5core.service.MemberService;
import com.core.aiProject5core.service.SaleService;
import com.core.aiProject5core.service.VehicleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@Controller
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;
    private final CounselingService counselingService;
    private final MemberService memberService;
    private final DealerService dealerService;
    private final CustomerService customerService;
    private final VehicleService vehicleService;
    

    // 구매 리스트 페이지
    @GetMapping("/list")
    public String saleList(Model model, Principal principal,
    						@PageableDefault(size = 10,
    										 sort = "id",
    										 direction = Sort.Direction.DESC)
    						Pageable pageable) {
    	String memberId = principal.getName();
    	
        Page<Sale> page = saleService.findByMemberId(pageable, memberId);
        
        model.addAttribute("saleList", page.getContent());
        model.addAttribute("salePage", page);
        return "member/saleList";
    }
    
    // 딜러 상담 후 구매확정
    @GetMapping("/create/{id}/{price}")
    public String salerCreate(@PathVariable("id") Long id,
    		 @PathVariable("price") int price) {
    	
    	Sale sale = new Sale();
    	
    	Counseling counseling = counselingService.findById(id);
    	counselingService.updateCounselingStatus(id, "구매완료");
    	sale.setCounseling(counseling);
    	
    	Optional<Dealer> dealer = dealerService.findById(counseling.getDealer().getId());
    	sale.setDealer(dealer.get());
    	
    	Optional<Customer> customer = customerService.findById(counseling.getCustomer().getId());
    	int idx = customer.get().getPurchaseCount();
    	customer.get().setPurchaseCount(idx++);
    	customerService.saveCustomer(customer.get(), memberService.findByMemberId(customer.get().getMember().getMemberId()));
    	sale.setCustomer(customer.get());
    	
    	Optional<Vehicle> vehicle = vehicleService.findById(counseling.getVehicleId());
    	sale.setVehicle(vehicle.get());
    	
    	sale.setPrice(price);
    	sale.setSaleDate(LocalDateTime.now());
    	
    	saleService.saveSale(sale);
    	
    	return "redirect:/dealer";
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
