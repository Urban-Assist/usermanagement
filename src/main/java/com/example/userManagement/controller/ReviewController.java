package com.example.userManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @GetMapping("/demo")
    public String demoEndpoint() {
        return "This is a demo endpoint!";
    }
}
