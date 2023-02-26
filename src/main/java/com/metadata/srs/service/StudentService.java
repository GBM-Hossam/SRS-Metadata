package com.metadata.srs.service;


import com.metadata.srs.dto.StudentRequestDTO;
import com.metadata.srs.dto.StudentResponseDTO;
import com.metadata.srs.entity.Student;
import com.metadata.srs.exceptions.StudentNotFoundException;
import com.metadata.srs.exceptions.StudentOperationException;
import com.metadata.srs.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
///service class for student CRUD ops
public class StudentService {

    @Autowired
    StudentRepository repository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<StudentResponseDTO> getAllStudents() {
        //retrieve students from SOR and convert to to DTO using Java streams
        List<Student> studentList = repository.findAll();
        logger.trace("Fetch all students :: " + studentList);
        List<StudentResponseDTO> studentResponseDTOList = studentList.stream()
                .map(student -> new StudentResponseDTO(student.getId(), student.getFullName(), student.getPhone(), student.getEmail(), student.getAge()))
                .collect(Collectors.toList());
        return studentResponseDTOList;
    }

    public StudentResponseDTO getStudentById(int id) throws StudentNotFoundException {
        //retrieve student by id from SOR and convert to to DTO, if course id not found throw exception
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found for this id :: " + id));
        logger.trace("Fetch student by id :: " + id + " , student :: " + student);

        return getStudentResponseDTO(student);
    }

    public void deleteStudentById(int id) throws StudentNotFoundException {
        //delete student by id from SOR, if course id not found throw exception
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found for this id :: " + id));

        repository.deleteById(id);
        logger.trace("Delete student by id :: " + id);
    }

    public StudentResponseDTO updateStudent(int id, StudentRequestDTO studentDetails) throws StudentNotFoundException, StudentOperationException {
        //update student by id from SOR and convert to DTO, if course id not found throw exception

        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found for this id :: " + id));

        student.setAge(studentDetails.getAge());
        student.setEmail(studentDetails.getEmail());
        student.setFullName(studentDetails.getFullName());
        student.setPhone(studentDetails.getPhone());
        try {
            student = repository.save(student);
            logger.trace("Update student :: " + student);
            return getStudentResponseDTO(student);
        } catch (Exception e) {
            throw new StudentOperationException(e.getMessage());
        }

    }


    public StudentResponseDTO addStudent(StudentRequestDTO studentRequestDTO) throws StudentOperationException {
        try {
            Student student = new Student();
            student.setPhone(studentRequestDTO.getPhone());
            student.setFullName(studentRequestDTO.getFullName());
            student.setEmail(studentRequestDTO.getEmail());
            student.setAge(studentRequestDTO.getAge());

            student = repository.save(student);
            logger.trace("Add student :: " + student);
            return getStudentResponseDTO(student);
        } catch (Exception e) {
            throw new StudentOperationException(e.getMessage());
        }
    }


    private StudentResponseDTO getStudentResponseDTO(Student student) {
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setPhone(student.getPhone());
        studentResponseDTO.setFullName(student.getFullName());
        studentResponseDTO.setEmail(student.getEmail());
        studentResponseDTO.setAge(student.getAge());
        studentResponseDTO.setId(student.getId());
        return studentResponseDTO;
    }
}
