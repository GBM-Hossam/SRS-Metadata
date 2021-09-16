package com.metadata.srs.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "srs_registration")
//@JsonIgnoreProperties({"pk"})
@Data
public class Registration {
    /// @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "Id")
    //private Integer id;
    @EmbeddedId
    private RegistrationId pk = new RegistrationId();


    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_Id")
    private Student student;

    @MapsId("courseId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_Id")
    private Course course;


    @Column(name = "registrationDate")
    private Date registrationDate;

    @Column(name = "score")
    private Integer score;

    @Override
    public String toString() {
        return "Registration{" +
                ", registrationDate=" + registrationDate +
                ", score=" + score +
                '}';
    }
}
