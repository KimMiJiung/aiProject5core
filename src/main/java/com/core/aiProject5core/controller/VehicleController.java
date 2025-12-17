package com.core.aiProject5core.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.core.aiProject5core.dto.VehicleOptionView;
import com.core.aiProject5core.entity.Vehicle;
import com.core.aiProject5core.service.VehicleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@Controller
@RequestMapping("/vehicles")
public class VehicleController {
	
	private final VehicleService vehicleService;

	
	// 메인 화면 페이징 처리
	@GetMapping
	public String requestVehicleList(
	        @RequestParam(name = "keyword", required = false) String keyword,
	        @RequestParam(name = "type", required = false) String type,
	        @RequestParam(name = "fuel", required = false) String fuel,
	        @PageableDefault(
	                page = 0,
	                size = 8,
	                sort = "name",
	                direction = Sort.Direction.ASC
	        ) Pageable pageable,
	        Model model) {
		// ALL 버튼용 처리: 빈 문자열은 null로 변환
		if(keyword != null && keyword.isBlank()) keyword = null;
		if(type != null && type.isBlank()) type = null;
		if(fuel != null && fuel.isBlank()) fuel = null;
		
	    Page<Vehicle> paging = vehicleService.getVehiclePage(keyword, type, fuel, pageable);

	    model.addAttribute("keyword", keyword);
	    model.addAttribute("type", type);
	    model.addAttribute("fuel", fuel);
	    model.addAttribute("paging", paging);
	    model.addAttribute("vehicleList", paging.getContent());
	    return "vehicle/vehicles";
	}


	// 차량 상세 정보 조회 - 모델 코드로 조회
	@GetMapping("/vehicle/{modelCode}")
	public String requestVehicleDetail(
			@PathVariable("modelCode") String modelCode,
			@RequestParam(name = "trim", required = false, defaultValue = "Standard") String trim, 
			Model model) {
		// 모델 코드로 차량 정보 획득
		Vehicle vehicle = vehicleService.getVehicleByModelCode(modelCode);
		// 트림 정보가 없다면 DB에서 획득
		if(trim == null || trim.isBlank()) {
            trim = vehicle.getTrim();
        }		
		// 차량의 옵션 리스트를 획득
		List<VehicleOptionView> optionList = vehicleService.getOptionList(vehicle, trim);
		
		model.addAttribute("optionList", optionList);
		model.addAttribute("trim", trim);
		model.addAttribute("vehicle", vehicle);
		return "vehicle/vehicle";
	}
	
	// 트림 선택 시 가격 + 트림 수정
	@PostMapping("/vehicle/{modelCode}/update-trim")
	public String updateTrimAndPriceForm(
			@PathVariable("modelCode") String modelCode,
			@RequestParam("id") Long id,
			@RequestParam("trim") String trim) {
		
		// service의 계산 + 수정 메서드로 최종 가격을 수정
		vehicleService.updateTrimPrice(id, trim);
		
		return "redirect:/vehicles/vehicle/" + modelCode + "?trim=" + trim;
	}
	
	// 차량 상세 정보 조회 - ID로 조회
	@GetMapping("/vehicle/detail/{id}")
	public String requestVehicleDetail1(@PathVariable("id") Long id, Model model) {
		
		// 모델 ID로 차량 정보 획득
		Optional<Vehicle> vehicle = vehicleService.getVehicleByVehicleId(id);
		model.addAttribute("vehicle", vehicle);
		return "redirect:/vehicles/vehicle/" + vehicle.get().getModelCode();
	}	
	
	
	// ##########################################################################################
	// 딜러 차량 등록 폼
	@GetMapping("/dealer/add")
	public String addVehicleForm(Model model) {
		model.addAttribute("vehicle", new Vehicle());
		return "vehicle/addVehicle";
	}
	
	private String fileDir = "c:/5core/";
	
	// 차량 등록 처리
	@PostMapping("/dealer/add")
	public String addVehiclePrdc(@Valid @ModelAttribute Vehicle vehicle, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "vehicle/addVehicle";
		}
		// 업로드 처리
		MultipartFile vehicleImage = vehicle.getVehicleImage();
		String saveName = vehicleImage.getOriginalFilename();
		File saveFile = new File(fileDir, saveName);
		
		if(vehicleImage != null && !vehicleImage.isEmpty()) {
			try {
				vehicleImage.transferTo(saveFile);
			} catch(IOException e) {
				e.printStackTrace();
				throw new RuntimeException("차량 이미지 업로드를 실패하였습니다.");
			}
			vehicle.setFileName(saveName);
		} else {
			vehicle.setFileName("no_image.jpg");
		}
		this.vehicleService.addVehicle(vehicle);
		return "redirect:/vehicles";
	}
	

}
