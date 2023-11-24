package com.metadata.srs.controller;

import com.metadata.srs.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")

public class IndexController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/")
    public String index() {
        return "Welcome to school registration APIs";
    }

}
