package com.core.aiProject5core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.aiProject5core.dto.VehicleOptionView;
import com.core.aiProject5core.entity.Vehicle;
import com.core.aiProject5core.entity.VehicleOption;
import com.core.aiProject5core.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@RequiredArgsConstructor
@Service
@Log
public class VehicleService {
	
	private final VehicleRepository vehicleRepository;
	
	
	//############################################################
	// 트림에 따른 가격 계산
	public int getPriceByTrim(String trim) {
		
		// 트림이 null인 경우 기본 가격
		if(trim == null) return 0;
		
		// switch expression을 사용하여 트림에 따른 가격 리턴
		return switch(trim.toUpperCase()) {
			case "EXCLUSIVE" -> 2000000;
			case "PRESTIGE" -> 4000000;
			default -> 0;   // Standard 트림은 기본 가격과 동일
		};
	}
	
	// 트림에 따라 계산된 가격을 DB에 업데이트
	@Transactional
	public void updateTrimPrice(Long id, String trim) {
		Vehicle vehicle = vehicleRepository.findById(id).get();
		int basePrice = vehicle.getBasePrice();    // 기본 가격
		int trimPrice = getPriceByTrim(trim);      // 트림 가격
		
		vehicle.setTrim(trim);
		vehicle.setFinalPrice(basePrice + trimPrice);
		
		vehicleRepository.updateTrimAndPrice(trim, vehicle.getFinalPrice(), id);
	}
	
	
	// 총액 계산 (기본가격 + 트림가격) - 화면 표시
	public int getTotalPrice(Long id, String trim) {
		Vehicle vehicle = vehicleRepository.findById(id).get();
		int basePrice = vehicle.getBasePrice();   // 기본 가격
		int trimPrice = getPriceByTrim(trim);     // 트림 가격
		
		return basePrice + trimPrice; 		      // 총액
	}
	//############################################################
	
	// 전체 상품 목록 조회 -> 페이징 처리 x
	public List<Vehicle> getVehicleList() {
		return vehicleRepository.findAll();
	}
	
	// 전체 상품 목록 조회 -> 페이징 처리
	public Page<Vehicle> getVehiclePage(String keyword, String type, String fuel, Pageable pageable) {
		// 조건이 null이거나 없다면 전체 목록을 조회
		if((keyword == null || keyword.isBlank()) && 
			(type == null || type.isBlank()) && 
			(fuel == null || fuel.isBlank())) {
			return vehicleRepository.findAll(pageable);
		}
		// 조건이 있다면 조건에 맞게 검색 결과를 조회
		return vehicleRepository.searchVehicle(keyword, type, fuel, pageable);
	}
	
	// 차량 상세 정보 조회
	public Vehicle getVehicleByModelCode(String modelCode) {
		return vehicleRepository.findWithOptionsByModelCode(modelCode).get();
	}
	
	// 차량ID에 해당하는 차량 1건 조회
	public Optional<Vehicle> getVehicleByVehicleId(Long vehicleId) {
		return vehicleRepository.findById(vehicleId);
	}
	
	// ####################################################################################################
	
	// 트림에 따른 옵션의 포함 여부
	public boolean isOptionIncluded(String optionTrim, String selectedTrim) {
	    if (optionTrim == null || selectedTrim == null) {
	        return false;
	    }
	    String option = optionTrim.toUpperCase().trim();
	    String selected = selectedTrim.toUpperCase().trim(); 

	    switch(option) {
	    case "STANDARD":  // Standard의 옵션은 모든 트림에서 포함
	        return true;
	    case "EXCLUSIVE": // Exclusive의 옵션은 익스클루시브와 프레스티지에 포함
	        return selected.equals("EXCLUSIVE") || selected.equals("PRESTIGE");
	    case "PRESTIGE":  // Prestige의 옵션은 프레스티지에서만 포함 
	        return selected.equals("PRESTIGE");
	    default:
	        return false;
	    }
	}
	// 트림에 따른 옵션 리스트 조회
	public List<VehicleOptionView> getOptionList(Vehicle vehicle, String selectedTrim) {
		List<VehicleOptionView> optionList = new ArrayList<>();
		// 화면에 보여줄 옵션 뷰 리스트 생성
		for(VehicleOption o : vehicle.getOptions()) {
			boolean included = isOptionIncluded(o.getTrimLevel(), selectedTrim);
			VehicleOptionView view = new VehicleOptionView(o.getOptionName(), included);
	        optionList.add(view);
		}
		return optionList;
	}
	
	
	
	
	
	
	// ###########################################################################################
	// 차량 등록
	public void addVehicle(Vehicle vehicle) {
		vehicleRepository.save(vehicle);
	}
	
	
	public Optional<Vehicle> findById(Long id) {
		return vehicleRepository.findById(id);
	}
}
