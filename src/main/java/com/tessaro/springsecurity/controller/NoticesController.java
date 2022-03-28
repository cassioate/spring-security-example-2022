package com.tessaro.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/notices")
public class NoticesController {

    @GetMapping
    public String contact() {
        return "Notices!";
    }

}
