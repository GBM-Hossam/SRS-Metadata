package com.metadata.srs.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class RegistrationResponseDTO implements Serializable {

    private Date registrationDate;
    private StudentResponseDTO studentResponseDTO;
    private CourseResponseDTO courseResponseDTO;
    private int score;
}
