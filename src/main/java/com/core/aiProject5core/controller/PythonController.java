package com.core.aiProject5core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.core.aiProject5core.component.PythonRunner;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PythonController {

    private final PythonRunner pythonRunner;

    @GetMapping("/run-python")
    public String runPython(@RequestParam(name = "name", defaultValue = "world") String name) {
        try {
            return pythonRunner.runHistogramPy(name);
        } catch (Exception e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }
}
