package com.metadata.srs.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class StudentResponseDTO implements Serializable {

    private Integer id;
    private String fullName;
    private String phone;
    private String email;
    private Integer age;

    public StudentResponseDTO(Integer id, String fullName, String phone, String email, Integer age) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.age = age;
    }

    public StudentResponseDTO() {
    }
}
