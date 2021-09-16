package com.metadata.srs.entity;

import lombok.Data;
import org.hibernate.validator.constraints.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "srs_student")
@Data
//@JsonIgnoreProperties({"registrations"})
public class Student implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "fullName")
    @NotEmpty(message = "Name may not be empty")
    @NotBlank(message = "Name may not be empty")
    @Length(min = 2 , max = 200)
    private String fullName;

    @Column(name = "phone")
    @Length(min = 2 , max = 15)
    private String phone;

    @Column(name = "email")
    @Length(min = 2 , max = 200)
    @Email(message = "Email must be valid")
    private String email;

    @Column(name = "age")
    @Range(min = 14, max = 21, message = "age must be between 14 to 21")
    private Integer age;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Registration> registrations = new HashSet<>();


    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
