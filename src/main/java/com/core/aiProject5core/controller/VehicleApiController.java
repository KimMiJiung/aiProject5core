package com.core.aiProject5core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.aiProject5core.entity.Vehicle;
import com.core.aiProject5core.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleApiController {
	
	private final VehicleRepository vehicleRepository;
	
	// AI 후보 차량 조회 API
    @GetMapping("/ai-candidates")
    public List<Map<String, Object>> getAiCandidates() {

        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Vehicle v : vehicles) {
            Map<String, Object> item = new HashMap<>();

            item.put("id", v.getId());
            item.put("modelCode", v.getModelCode());
            item.put("name", v.getName());
            item.put("vehicleType", v.getVehicleType());
            item.put("fuelType", v.getFuelType());
            item.put("fileName", v.getFileName());
            item.put("finalPrice", v.getFinalPrice());

            result.add(item);
        }

        // 결과를 10대로 한정
        if (result.size() > 10) {
            return result.subList(0, 10);
        }

        return result;
    }
}
