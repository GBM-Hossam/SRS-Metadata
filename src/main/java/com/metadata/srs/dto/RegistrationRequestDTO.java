package com.metadata.srs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class RegistrationRequestDTO implements Serializable {
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ApiModelProperty(required = true, example = "2022/09/16" , notes =  "Add registration date",allowEmptyValue = false)
    private Date registrationDate;
    private int score;
}
