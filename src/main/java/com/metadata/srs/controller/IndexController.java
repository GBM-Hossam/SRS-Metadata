package com.metadata.srs.controller;


import com.metadata.srs.dto.CourseResponseDTO;
import com.metadata.srs.dto.StudentResponseDTO;
import com.metadata.srs.exceptions.CourseNotFoundException;
import com.metadata.srs.exceptions.StudentNotFoundException;
import com.metadata.srs.service.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/")

public class IndexController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/")
    public String index() {
        return "Welcome to School registration APIs";
    }

}
