package com.metadata.srs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseResponseDTO implements Serializable {

    private Integer id;
    private String name;
    private Integer duration;
    private Double price;

    public CourseResponseDTO(Integer id, String name, Integer duration, Double price) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public CourseResponseDTO() {
    }
}
