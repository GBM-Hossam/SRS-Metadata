package com.metadata.srs.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StudentRequestDTO implements Serializable {

    @ApiModelProperty(required = true , notes =  "Name is required, Length(min = 2 , max = 200)", allowEmptyValue = false)
    private String fullName;

    @ApiModelProperty(required = true , notes =  " Length(min = 2 , max = 15)", allowEmptyValue = false)
    private String phone;

    @ApiModelProperty(required = true , notes =  "Mail format must be proper, Length(min = 2 , max = 200)", allowEmptyValue = false)
    private String email;

    @ApiModelProperty(required = true , notes =  "Age between 14 to 21", allowEmptyValue = false)
    private Integer age;
}
