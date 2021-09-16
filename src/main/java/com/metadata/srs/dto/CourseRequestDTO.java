package com.metadata.srs.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseRequestDTO implements Serializable {

    @ApiModelProperty(required = true , notes =  "Name is required, Length(min = 2 , max = 200)", allowEmptyValue = false)
    private String name;
    private Integer duration;
    private Double price;
}
