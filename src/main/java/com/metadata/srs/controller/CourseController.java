package com.metadata.srs.controller;

import com.metadata.srs.dto.CourseRequestDTO;
import com.metadata.srs.dto.CourseResponseDTO;
import com.metadata.srs.exceptions.CourseNotFoundException;
import com.metadata.srs.exceptions.CourseOperationException;
import com.metadata.srs.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
@Api(value = "Course", description = "APIs for Course CRUD", tags = {"Course"})

///rest API for course
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public List<CourseResponseDTO> findAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/courses")
    public ResponseEntity<?> addCourse(@RequestBody CourseRequestDTO courseDetails) {
        try {
            CourseResponseDTO student = courseService.addCourse(courseDetails);
            return ResponseEntity.status(201).body(student);
        } catch (CourseOperationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseResponseDTO> findCourseById(@PathVariable int id) {
        try {
            CourseResponseDTO course = courseService.getCourseById(id);
            return ResponseEntity.ok().body(course);

        } catch (CourseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable int id, @RequestBody CourseRequestDTO courseDetails) {
        try {
            CourseResponseDTO course = courseService.updateCourse(id, courseDetails);
            return ResponseEntity.ok().body(course);

        } catch (CourseNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CourseOperationException e) {
            return ResponseEntity.status(400).body(e.getMessage());

        }
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id) {
        try {
            courseService.deleteCourseById(id);
            return ResponseEntity.ok().build();

        } catch (CourseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

