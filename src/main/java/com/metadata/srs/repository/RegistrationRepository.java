package com.metadata.srs.repository;

import com.metadata.srs.entity.Course;
import com.metadata.srs.entity.Registration;
import com.metadata.srs.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

    String Q_GET_FREE_STUDENTS = "SELECT u FROM Student u left JOIN Registration r on u.id = r.student.id where r.course.id is null";
    String Q_GET_FREE_COURSES = "SELECT c FROM Course c left JOIN Registration r on c.id = r.course.id where r.student.id is null";


    @Query(Q_GET_FREE_STUDENTS)
    List<Student> getAllFreeStudents();

    @Query(Q_GET_FREE_COURSES)
    List<Course> getAllEmptyCourses();
}
