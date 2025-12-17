package com.core.aiProject5core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.aiProject5core.dto.ChargerDto;
import com.core.aiProject5core.mybatis.ISearchRadius;
import com.core.aiProject5core.service.EvChargerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chargers")
public class ChargerController {
	
	@Autowired
	ISearchRadius dao;
	
	private final EvChargerService evChargerService;
	
	@GetMapping("/index")
	public String index() {
		return "chargers/index"; 
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		List<ChargerDto> chargers = evChargerService.fetchChargers();
		model.addAttribute("chargers", chargers);
		return "chargers/list";
	}
	
	@RequestMapping("/home")
	public String home() {
		return "chargers/home";
	}
	
    private static final String apiKey = "AIzaSyAvBThYJvmpqLzaPNdpjsJJd7MVTe_a3kI";
	
	//내위치값 알아내기
    @GetMapping("/01GeoLocation")
    public String geoFunc1(Model model) {
        model.addAttribute("apiKey", apiKey);
        return "chargers/01GeoLocation"; 
    }

    //구글맵 연동
    @GetMapping("/02GoogleMap")
    public String geoFunc2(Model model) {
        model.addAttribute("apiKey", apiKey);
        return "chargers/02GoogleMap"; 
    }

    //구글맵에 내위치 출력하기
    @GetMapping("/03MyLocation")
    public String geoFunc3(Model model) {
        model.addAttribute("apiKey", apiKey);
        return "chargers/03MyLocation"; 
    }

    @GetMapping("/searchMap")
    public String geoFunc4(Model model) {
		List<ChargerDto> chargersList = evChargerService.fetchChargers();
		model.addAttribute("chargersList", chargersList);
        model.addAttribute("apiKey", apiKey);       
        return "chargers/searchMap"; 
    }

}
