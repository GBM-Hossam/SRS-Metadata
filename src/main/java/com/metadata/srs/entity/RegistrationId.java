package com.metadata.srs.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class RegistrationId implements Serializable {

    @Column(name = "course_Id")
    @Cascade(CascadeType.REMOVE)
    private Integer courseId;

    @Column(name = "student_Id")
    @Cascade(CascadeType.REMOVE)
    private Integer studentId;
}
