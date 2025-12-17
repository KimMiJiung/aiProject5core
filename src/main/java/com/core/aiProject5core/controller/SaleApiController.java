package com.core.aiProject5core.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.core.aiProject5core.entity.Dealer;
import com.core.aiProject5core.entity.Member;
import com.core.aiProject5core.entity.Sale;
import com.core.aiProject5core.repository.DealerRepository;
import com.core.aiProject5core.repository.MemberRepository;
import com.core.aiProject5core.repository.SaleRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales")
public class SaleApiController {
    
    private final SaleRepository saleRepository;
    private final MemberRepository memberRepository;
    private final DealerRepository dealerRepository;
    
    /*
     * 딜러별 sale 판매 원형 그래프
     */
    @GetMapping(value = "/vehicle-sales/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getMyVehicleSales(
            HttpServletRequest request,  // Authentication → HttpServletRequest로 변경
            @RequestParam(value = "dealerId", required = false) Long dealerId) {
        
        Long targetDealerId = null;
        
        // 1. dealerId 파라미터 우선 (Streamlit에서 전달)
        if (dealerId != null) {
            targetDealerId = dealerId;
            System.out.println("✅ dealerId 파라미터=" + dealerId);
        } 
        // 2. 세션/헤더에서 딜러ID 추출 (Spring Security 세션)
        else {
            // 방법1: 세션에서 memberId 가져오기
            HttpSession session = request.getSession(false);
            if (session != null) {
                String memberId = (String) session.getAttribute("memberId");
                if (memberId != null) {
                    Optional<Member> mOpt = memberRepository.findByMemberId(memberId);
                    if (mOpt.isPresent()) {
                        Optional<Dealer> dOpt = dealerRepository.findByMember(mOpt.get());
                        if (dOpt.isPresent()) {
                            targetDealerId = dOpt.get().getId();
                            System.out.println("✅ 세션 memberId=" + memberId + " → dealerId=" + targetDealerId);
                        }
                    }
                }
            }
            
            // 방법2: Authorization 헤더 (JWT Bearer)
            if (targetDealerId == null) {
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    // JWT 토큰 파싱 로직 (간단히 username 추출)
                    System.out.println("JWT 토큰 발견: " + authHeader.substring(0, 50) + "...");
                    // 실제 JWT 파싱은 별도 구현 필요
                }
            }
        }
        
        if (targetDealerId == null) {
            System.out.println("❌ 딜러 식별 실패 → 빈 데이터 반환");
            return new ArrayList<>();
        }
        
        // 기존 로직 그대로
        Dealer dealer = dealerRepository.findById(targetDealerId).orElse(null);
        if (dealer == null) {
            System.out.println("❌ 딜러 없음: " + targetDealerId);
            return new ArrayList<>();
        }
        
        List<Sale> sales = saleRepository.findByDealer(dealer);
        System.out.println("📊 판매 데이터 수: " + sales.size() + " (딜러=" + targetDealerId + ")");
        
        // vehicleStats 집계 로직 (기존 그대로)
        Map<Long, Map<String, Object>> vehicleStats = new HashMap<>();
        for (Sale sale : sales) {
            Long vid = sale.getVehicle().getId();
            String vehicleName = sale.getVehicle().getName() != null 
                ? sale.getVehicle().getName() 
                : "차량_" + vid;
                
            Map<String, Object> stat = vehicleStats.computeIfAbsent(vid, k -> {
                Map<String, Object> newStat = new HashMap<>();
                newStat.put("vehicleId", k);
                newStat.put("vehicleName", vehicleName);
                newStat.put("salesCount", 0L);
                newStat.put("totalPrice", 0L);
                return newStat;
            });
            
            Long currentCount = (Long) stat.get("salesCount");
            stat.put("salesCount", currentCount != null ? currentCount + 1 : 1L);
            
            Long price = sale.getPrice() != 0L ? (long) sale.getPrice() : 0L;
            Long currentPrice = (Long) stat.get("totalPrice");
            stat.put("totalPrice", currentPrice != null ? currentPrice + price : price);
        }
        
        List<Map<String, Object>> result = new ArrayList<>(vehicleStats.values());
        System.out.println("✅ 최종 반환: " + result.size() + "종 차량 (딜러=" + targetDealerId + ")");
        return result;
    }












    
    
    
    // 딜러별 월매출 그래프
    @GetMapping(value = "/monthly-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getMonthlySales(Principal principal,
            @RequestParam(value = "dealerId", required = false) Long dealerId,
            @RequestParam(value = "memberId", required = false) Long memberId,
            @RequestParam(value = "year", defaultValue = "2025") int year) {

        try {
            // 1) dealerId 직접 들어오면 그걸 우선 사용
            if (dealerId != null) {
                System.out.println("dealerId 파라미터=" + dealerId);
            } else if (memberId != null) {
                // 2) memberId → Dealer
                System.out.println("memberId=" + memberId + " → 딜러 조회");
                Member m = memberRepository.findById(memberId).orElse(null);
                if (m != null) {
                    Dealer d = dealerRepository.findByMember(m).orElse(null);
                    if (d != null) dealerId = d.getId();
                }
            } else if (principal != null) {
                // 3) 로그인 사용자 → Dealer
                System.out.println("principal=" + principal.getName() + " → 딜러 조회");
                Member m = memberRepository.findByMemberId(principal.getName()).orElse(null);
                if (m != null) {
                    Dealer d = dealerRepository.findByMember(m).orElse(null);
                    if (d != null) dealerId = d.getId();
                }
            }

            // 딜러 못 찾으면 0 리턴
            if (dealerId == null) {
                System.out.println("딜러 식별 실패 → 빈 데이터");
                return createEmptyMonthlyData();
            }

            System.out.println("월별실적 조회: dealerId=" + dealerId + ", year=" + year);
            List<Object[]> rows = saleRepository.findMonthlySalesByDealer(year, dealerId);

            String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            List<Map<String,Object>> result = new ArrayList<>();

            for (String m : months) {
                Map<String,Object> rowMap = new HashMap<>();
                rowMap.put("month", m);
                rowMap.put("salesCount", 0L);
                rowMap.put("totalPrice", 0L);

                for (Object[] r : rows) {
                    String dbMonth = r[0].toString().trim();
                    if (dbMonth.equals(m)) {
                        rowMap.put("salesCount", ((Number) r[1]).longValue());
                        rowMap.put("totalPrice", ((Number) r[2]).longValue());
                        break;
                    }
                }
                result.add(rowMap);
            }

            // 성장률
            if (result.size() == 12) {
                long dec = (Long) result.get(11).get("salesCount");
                long nov = (Long) result.get(10).get("salesCount");
                double gr = (nov > 0) ? (dec - nov) * 100.0 / nov : (dec > 0 ? 100.0 : 0.0);
                result.get(11).put("growthRate", Math.round(gr * 10.0) / 10.0);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("최종 dealerId: dealerId=" + dealerId);
            return createEmptyMonthlyData();
        }
    }

    private List<Map<String, Object>> createEmptyMonthlyData() {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        List<Map<String, Object>> emptyData = new ArrayList<>();
        for (String month : months) {
            Map<String, Object> data = new HashMap<>();
            data.put("month", month);
            data.put("salesCount", 0L);
            data.put("totalPrice", 0L);
            emptyData.add(data);
        }
        return emptyData;
    }







    
    
    
}
