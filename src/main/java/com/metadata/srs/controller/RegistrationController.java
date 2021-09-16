package com.metadata.srs.controller;


import com.metadata.srs.dto.RegistrationRequestDTO;
import com.metadata.srs.dto.RegistrationResponseDTO;
import com.metadata.srs.exceptions.RegistrationOperationException;
import com.metadata.srs.service.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@Api(value = "Registration", description = "APIs for Course and Student Registration", tags = {"Registration"})

///rest API for registration
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("registration/course/{courseId}/student/{studentId}")
    @ApiOperation(value = "Create course and student registration", tags = {""})
    public ResponseEntity<?> addRegistration(@PathVariable int courseId, @PathVariable int studentId,
                                             @RequestBody RegistrationRequestDTO registrationRequestDTO) {
        try {
            RegistrationResponseDTO registration = registrationService.addRegistration(courseId, studentId, registrationRequestDTO);
            return ResponseEntity.status(201).body(registration);
        } catch (RegistrationOperationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
