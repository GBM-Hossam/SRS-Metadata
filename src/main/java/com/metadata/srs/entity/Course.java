package com.metadata.srs.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.NotBlank;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "srs_course")
@Data
//@EqualsAndHashCode(exclude = "registrations")
//@JsonIgnoreProperties({"registrations"})

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Name may not be empty")
    @NotBlank(message = "Name may not be empty")
    @Length(min = 2 , max = 200)
    private String name;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "price")
    private Double price;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Registration> registrations = new HashSet<>();


    public Course() {
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
