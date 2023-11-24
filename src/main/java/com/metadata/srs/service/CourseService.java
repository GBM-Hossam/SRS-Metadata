package com.metadata.srs.service;


import com.metadata.srs.dto.CourseRequestDTO;
import com.metadata.srs.dto.CourseResponseDTO;
import com.metadata.srs.entity.Course;
import com.metadata.srs.exceptions.CourseNotFoundException;
import com.metadata.srs.exceptions.CourseOperationException;
import com.metadata.srs.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

///service class for course CRUD ops
public class CourseService {

    final CourseRepository repository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<CourseResponseDTO> getAllCourses() {

        //retrieve courses from SOR and convert to to DTO using Java streams

        List<Course> courseList = repository.findAll();
        logger.trace("Fetch all courses {} ", courseList);

        return courseList.stream()
                .map(course -> new CourseResponseDTO(course.getId(), course.getName(), course.getDuration(), course.getPrice()))
                .collect(Collectors.toList());
    }

    public CourseResponseDTO getCourseById(int id) throws CourseNotFoundException {
        //retrieve course by id from SOR and convert to to DTO, if course id not found throw exception
        Course course = repository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found for this id :: " + id));
        logger.trace("Fetch course by id {} , course {}" , id  ,  course);

        return getCourseResponseDTO(course);
    }

    public void deleteCourseById(int id) throws CourseNotFoundException {
        //delete course by id from SOR, if course id not found throw exception
        repository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found for this id :"+ id));

        repository.deleteById(id);
        logger.trace("Delete course by id {} ", id);
    }

    public CourseResponseDTO updateCourse(int id, CourseRequestDTO courseDetails) throws CourseNotFoundException, CourseOperationException {
        //update course by id from SOR and convert to DTO, if course id not found throw exception

        Course course = repository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found for this id :: " + id));

        course.setDuration(courseDetails.getDuration());
        course.setName(courseDetails.getName());
        course.setPrice(courseDetails.getPrice());
        try {
            course = repository.save(course);
            logger.trace("Update course {} " , course);
            return getCourseResponseDTO(course);
        } catch (Exception e) {
            throw new CourseOperationException(e.getMessage());
        }
    }

    public CourseResponseDTO addCourse(CourseRequestDTO courseRequestDTO) throws CourseOperationException {
        try {
            Course course = new Course();
            course.setName(courseRequestDTO.getName());
            course.setDuration(courseRequestDTO.getDuration());
            course.setPrice(courseRequestDTO.getPrice());

            course = repository.save(course);
            logger.trace("Add course {} ", course);
            return getCourseResponseDTO(course);
        } catch (Exception e) {
            throw new CourseOperationException(e.getMessage());
        }
    }


    private CourseResponseDTO getCourseResponseDTO(Course course) {
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setDuration(course.getDuration());
        courseResponseDTO.setId(course.getId());
        courseResponseDTO.setName(course.getName());
        courseResponseDTO.setPrice(course.getPrice());
        return courseResponseDTO;
    }
}
