package com.metadata.srs.service;


import com.metadata.srs.AppConstants;
import com.metadata.srs.dto.CourseResponseDTO;
import com.metadata.srs.dto.RegistrationRequestDTO;
import com.metadata.srs.dto.RegistrationResponseDTO;
import com.metadata.srs.dto.StudentResponseDTO;
import com.metadata.srs.entity.Course;
import com.metadata.srs.entity.Registration;
import com.metadata.srs.entity.RegistrationId;
import com.metadata.srs.entity.Student;
import com.metadata.srs.exceptions.CourseNotFoundException;
import com.metadata.srs.exceptions.RegistrationOperationException;
import com.metadata.srs.exceptions.StudentNotFoundException;
import com.metadata.srs.repository.CourseRepository;
import com.metadata.srs.repository.RegistrationRepository;
import com.metadata.srs.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

///service class for registration and Admin filter reports
public class RegistrationService {

    @Autowired
    RegistrationRepository registrationrepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;


    public RegistrationResponseDTO addRegistration(int courseId, int studentId, RegistrationRequestDTO registrationRequestDTO) throws RegistrationOperationException {
        //create registration, and convert to proper DTO
        try {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new CourseNotFoundException("Course not found for this id :: " + courseId));
            System.out.println("Fetch course by id :: " + courseId + " , course :: " + course);

            boolean isCourseFull = course.getRegistrations().size() >= AppConstants.MAX_COURSE_CAPACITY;
            if (isCourseFull)
                throw new RegistrationOperationException("Course reach to max capacity " + AppConstants.MAX_COURSE_CAPACITY + " at the moment!!");


            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentNotFoundException("Student not found for this id :: " + studentId));
            System.out.println("Fetch student by id :: " + studentId + " , student :: " + student);


            boolean isStudentFull = student.getRegistrations().size() >= AppConstants.MAX_STUDENT_REGISTRATION;
            if (isStudentFull)
                throw new RegistrationOperationException("Student can't register in more than " + AppConstants.MAX_STUDENT_REGISTRATION + " courses!!");


            RegistrationId registrationId = new RegistrationId();
            registrationId.setCourseId(course.getId());
            registrationId.setStudentId(student.getId());

            Registration registration = new Registration();
            registration.setRegistrationDate(registrationRequestDTO.getRegistrationDate());
            registration.setScore(registrationRequestDTO.getScore());
            registration.setPk(registrationId);
            registration.setStudent(student);
            registration.setCourse(course);
            registrationrepository.save(registration);
            System.out.println("Add registration :: " + registration);

            CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
            courseResponseDTO.setId(registrationId.getCourseId());
            StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
            studentResponseDTO.setId(registrationId.getStudentId());

            RegistrationResponseDTO registrationResponseDTO = new RegistrationResponseDTO();
            registrationResponseDTO.setScore(registrationRequestDTO.getScore());
            registrationResponseDTO.setRegistrationDate(registrationRequestDTO.getRegistrationDate());
            registrationResponseDTO.setCourseResponseDTO(courseResponseDTO);
            registrationResponseDTO.setStudentResponseDTO(studentResponseDTO);

            return registrationResponseDTO;
        } catch (Exception e) {
            throw new RegistrationOperationException(e.getMessage());
        }

    }

    public List<StudentResponseDTO> getCourseStudents(int courseId) throws CourseNotFoundException {
        //retrieve a course students, and convert to proper DTO
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found for this id :: " + courseId));
        System.out.println("Fetch course by id :: " + courseId + " , course :: " + course);

        //System.out.println(course.getRegistrations());
        List<StudentResponseDTO> studentList = new ArrayList<>();

        Set<Registration> registrationSet = course.getRegistrations();

        for (Registration registration : registrationSet) {
            Student student = registration.getStudent();
            //System.out.println(student);
            StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
            studentResponseDTO.setAge(student.getAge());
            studentResponseDTO.setEmail(student.getEmail());
            studentResponseDTO.setId(student.getId());
            studentResponseDTO.setFullName(student.getFullName());
            studentResponseDTO.setPhone(student.getPhone());
            studentList.add(studentResponseDTO);

        }
        return studentList;
    }

    public List<CourseResponseDTO> getStudentCourses(int studentId) throws StudentNotFoundException {
        //retrieve a student courses, and convert to proper DTO
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found for this id :: " + studentId));
        System.out.println("Fetch student by id :: " + studentId + " , student :: " + student);

        List<CourseResponseDTO> courseList = new ArrayList<>();

        Set<Registration> registrationSet = student.getRegistrations();

        for (Registration registration : registrationSet) {
            Course course = registration.getCourse();
            // System.out.println(course);
            CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
            courseResponseDTO.setDuration(course.getDuration());
            courseResponseDTO.setId(course.getId());
            courseResponseDTO.setName(course.getName());
            courseResponseDTO.setPrice(course.getPrice());
            courseList.add(courseResponseDTO);
        }
        return courseList;
    }

    public List<StudentResponseDTO> getFreeStudents() {
        //retrieve all students not register in any class using custom JPQL
        List<Student> studentList = registrationrepository.getAllFreeStudents();
        return studentList.stream()
                .map(student -> new StudentResponseDTO(student.getId(), student.getFullName(), student.getPhone(), student.getEmail(), student.getAge()))
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getEmptyCourses() {
        //retrieve all courses with not students interest to register yet using custom JPQL
        List<Course> courseList = registrationrepository.getAllEmptyCourses();
        return courseList.stream()
                .map(course -> new CourseResponseDTO(course.getId(), course.getName(), course.getDuration(), course.getPrice()))
                .collect(Collectors.toList());
    }
}
