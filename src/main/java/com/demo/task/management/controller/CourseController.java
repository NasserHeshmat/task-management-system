package com.demo.task.management.controller;

import com.demo.task.management.entity.Course;
import com.demo.task.management.model.ConfirmationResponse;
import com.demo.task.management.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import static com.demo.task.management.constant.ErrorMessages.*;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/register")
    public ResponseEntity<ConfirmationResponse> registerToCourse(@Valid @RequestParam @Min(value = 1) Long courseId) {
        courseService.registerToCourse(courseId);
        return ResponseEntity.ok(new ConfirmationResponse(REGISTERED_TO_COURSE_SUCCESSFULLY));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ConfirmationResponse> cancelCourseRegistration(@Valid @RequestParam @Min(value = 1) Long courseId) {
    courseService.cancelCourseRegisteration(courseId);
    return ResponseEntity.ok(new ConfirmationResponse(REGISTRATION_CANCELED_SUCCESSFULLY));
    }

    @GetMapping("/schedule/pdf")
    public ResponseEntity<byte[]> getCourseScheduleAsPdf(@Valid @RequestParam @Min(value = 1) Long courseId) {
        byte[] pdfContent = courseService.getCourseScheduleAsPdf(courseId);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=course_schedule.pdf")
                .body(pdfContent);
    }
}
