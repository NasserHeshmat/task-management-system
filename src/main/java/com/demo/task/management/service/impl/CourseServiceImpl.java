package com.demo.task.management.service.impl;

import com.demo.task.management.entity.Course;
import com.demo.task.management.entity.CourseSchedule;
import com.demo.task.management.entity.Student;
import com.demo.task.management.exception.CustomException;
import com.demo.task.management.repository.CourseRepository;
import com.demo.task.management.repository.StudentRepository;
import com.demo.task.management.service.CourseService;
import com.demo.task.management.util.PdfUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.task.management.constant.ErrorMessages.*;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final PdfUtil pdfUtil;
    @Override
    public void registerToCourse(Long courseId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByEmail(username);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_COURSE, HttpStatus.NOT_FOUND));
        if(student.getEnrolledCourses().contains(course))
            throw new CustomException(ALREADY_REGISTERED_TO_COURSE, HttpStatus.CONFLICT);
        student.getEnrolledCourses().add(course);
        studentRepository.save(student);

    }

    @Cacheable(value = "courses", key = "'allCourses'")
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public byte[] getCourseScheduleAsPdf(Long courseId) {

        Course course = courseRepository.getById(courseId);
        List<CourseSchedule> courseScheduleList = course.getSchedule();
        return pdfUtil.generateCourseSchedulePdf(courseScheduleList,course.getCourseName());
    }

    @Override
    public void cancelCourseRegisteration(Long courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByEmail(username);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_COURSE, HttpStatus.NOT_FOUND));
        if (!student.getEnrolledCourses().contains(course))
            throw new CustomException(NOT_REGISTERED_TO_COURSE, HttpStatus.BAD_REQUEST);
        student.getEnrolledCourses().remove(course);
        studentRepository.save(student);
    }


}
