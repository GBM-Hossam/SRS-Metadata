package com.metadata.srs.controller;


import com.metadata.srs.dto.CourseResponseDTO;
import com.metadata.srs.dto.StudentResponseDTO;
import com.metadata.srs.exceptions.CourseNotFoundException;
import com.metadata.srs.exceptions.StudentNotFoundException;
import com.metadata.srs.service.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@Api(value = "Admin Reports", description = "APIs for Admin Reports", tags = {"Reports"})

///rest API for admin
public class AdminController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("admin/course/{courseId}")
    @ApiOperation(value = "Filter all students with a specific course", tags = {""})
    public ResponseEntity<?> getCourseStudents(@PathVariable int courseId) {
        try {
            List<StudentResponseDTO> studentList = registrationService.getCourseStudents(courseId);
            //return studentList;
            return ResponseEntity.ok().body(studentList);
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("report/student/{studentId}")
    @ApiOperation(value = "Filter all courses for a specific student", tags = {""})
    public ResponseEntity<?> getStudentCourses(@PathVariable int studentId) {
        try {
            List<CourseResponseDTO> courseList = registrationService.getStudentCourses(studentId);
            return ResponseEntity.ok().body(courseList);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("report/students/events/free")
    @ApiOperation(value = "Filter all free students, not registered in any courses", tags = {""})
    public ResponseEntity<?> getFreeStudents() {

        List<StudentResponseDTO> studentList = registrationService.getFreeStudents();
        return ResponseEntity.ok().body(studentList);

    }

    @GetMapping("report/courses/events/free")
    @ApiOperation(value = "Filter all empty classes, no student register in it", tags = {""})
    public ResponseEntity<?> getEmptyCourses() {
        List<CourseResponseDTO> courseList = registrationService.getEmptyCourses();
        return ResponseEntity.ok().body(courseList);
    }
}
