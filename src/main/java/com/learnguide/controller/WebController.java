package com.learnguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/course/{courseId}")
    public String courseDetail(@PathVariable String courseId) {
        return "course-detail.html";
    }
}
