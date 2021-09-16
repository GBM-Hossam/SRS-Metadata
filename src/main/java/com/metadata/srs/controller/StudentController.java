package com.metadata.srs.controller;

import com.metadata.srs.dto.StudentRequestDTO;
import com.metadata.srs.dto.StudentResponseDTO;
import com.metadata.srs.exceptions.StudentNotFoundException;
import com.metadata.srs.exceptions.StudentOperationException;
import com.metadata.srs.service.StudentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
@Api(value = "Student", description = "APIs for Student CRUD", tags = {"Student"})

///rest API for student
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<StudentResponseDTO> findAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/students")
    public ResponseEntity<?> addStudent(@RequestBody StudentRequestDTO studentDetails) {
        StudentResponseDTO student = null;
        try {
            student = studentService.addStudent(studentDetails);
        } catch (StudentOperationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.status(201).body(student);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponseDTO> findStudentById(@PathVariable int id) {
        try {
            StudentResponseDTO student = studentService.getStudentById(id);
            return ResponseEntity.ok().body(student);

        } catch (StudentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody StudentRequestDTO studentDetails) {
        try {
            StudentResponseDTO student = studentService.updateStudent(id, studentDetails);
            return ResponseEntity.ok().body(student);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (StudentOperationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        try {
            studentService.deleteStudentById(id);
            return ResponseEntity.ok().build();

        } catch (StudentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
