package com.core.aiProject5core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/event")
public class EventController {
	@GetMapping
    public String eventPage() {
        return "event/event"; 
    }
	
    @GetMapping("/eventDetail")
    public String eventDetail() {
        return "event/eventDetail"; // eventDetail.html로 이동
    }
}
