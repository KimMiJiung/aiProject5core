package com.core.aiProject5core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.aiProject5core.component.PythonRunner;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PythonViewController {

    private final PythonRunner pythonRunner;

    @GetMapping("/python-histogram")
    public String showHistogramPage(Model model) {
        model.addAttribute("result", "버튼을 눌러서 실행해보세요!");
        return "python/histogram";
    }

    @GetMapping("/run-histogram")
    public String runHistogram(@RequestParam(name = "name", required = false) String name, Model model) {
        try {
            String result = pythonRunner.runHistogramPy(name);
            model.addAttribute("result", result);
        } catch (Exception e) {
            model.addAttribute("result", "에러: " + e.getMessage());
        }
        return "python/histogram";  // 같은 페이지 새로고침
    }
}
