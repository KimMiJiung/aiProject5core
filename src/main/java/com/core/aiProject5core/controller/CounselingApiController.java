package com.core.aiProject5core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.aiProject5core.entity.Counseling;
import com.core.aiProject5core.entity.HopeArea;
import com.core.aiProject5core.repository.CounselingRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/counseling")
public class CounselingApiController {
	
	private final CounselingRepository counselingRepository;
	
	// 상담ID로 상담 상세 조회
    @GetMapping("/{counselingId}")
    public Map<String, Object> getCounseling(@PathVariable("counselingId") Long counselingId) {

        Counseling c = counselingRepository.findById(counselingId).get();

        Map<String, Object> result = new HashMap<>();

        // Counseling 기본 필드들
        result.put("id", c.getId());
        result.put("vehicleId", c.getVehicleId());
        result.put("vehicleName", c.getVehicleName());
        result.put("purchasePurpose", c.getPurchasePurpose());
        result.put("otherInput", c.getOtherInput());
        result.put("purchasePeriod", c.getPurchasePeriod());
        result.put("vehicleType", c.getVehicleType());
        result.put("engineType", c.getEngineType());
        result.put("counselingLikeTime", c.getCounselingLikeTime());
        result.put("status", c.getStatus());

        // HopeArea 정보 (없을 수도 있으니까 null 체크)
        HopeArea h = c.getHopearea();
        if (h != null) {
            Map<String, Object> hopearea = new HashMap<>();
            hopearea.put("sido", h.getSido());
            hopearea.put("siGunGu", h.getSiGunGu());
            hopearea.put("eupMyeonDong", h.getEupMyeonDong());
            result.put("hopearea", hopearea);
        } else {
            result.put("hopearea", null);
        }

        return result;
    }
	
	// AI 추천 리스트 저장
	// - json을 문자열로 받아서 바로 저장
	@PostMapping("/{counselingId}/ai-recommendations")
	public void saveAiRecommend(@PathVariable("counselingId") Long counselingId, @RequestBody String json) {
		Counseling c = counselingRepository.findById(counselingId).get();
		
		c.setAiRecommendJson(json);
		counselingRepository.save(c);
	}
	
	
	// 딜러가 선택한 최종 추천 차량 저장
	// - json을 문자열로 받아서 바로 저장
	@PostMapping("/{counselingId}/final-recommendations")
	public void saveFinalRecommend(@PathVariable("counselingId") Long counselingId, @RequestBody String json) {
		Counseling c = counselingRepository.findById(counselingId).get();
		
		c.setFinalRecommendJson(json);
		counselingRepository.save(c);
	}
	
	
	
	
	
	
	
}
