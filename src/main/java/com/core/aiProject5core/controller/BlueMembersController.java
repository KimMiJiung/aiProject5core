package com.core.aiProject5core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/bluemembers")
public class BlueMembersController {
	@GetMapping
    public String eventPage() {
        return "bluemembers/bluemembers"; 
    }
}
