package com.learnguide.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnguide.dto.CourseGenerationRequest;
import com.learnguide.model.Course;
import com.learnguide.model.DifficultyLevel;
import com.learnguide.model.LearningPreferences;
import com.learnguide.service.CourseGenerator;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseGenerator courseGenerator;

    @PostMapping("/generate")
    public ResponseEntity<?> generateCourse(@RequestBody CourseGenerationRequest request) {
        try {
            LearningPreferences preferences = new LearningPreferences();
            preferences.setPreferredDifficulty(
    request.getDifficulty() != null ? request.getDifficulty() : DifficultyLevel.BEGINNER
);

            
            Course course = courseGenerator.generateCustomizedCourse(
                request.getTopic(),
                preferences
            );
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("Failed to generate course: " + e.getMessage()));
        }
    }

    @GetMapping("/enrolled/{userId}")
    public ResponseEntity<List<Course>> getEnrolledCourses(@PathVariable String userId) {
        List<Course> courses = new ArrayList<>();
        Course course = new Course(
            "Sample Course",
            "This is a sample course description",
            DifficultyLevel.BEGINNER
        );
        courses.add(course);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseDetail(@PathVariable String courseId) {
        try {
            // For now, return the same course that was generated
            // In a real app, you would fetch this from a database
            Course course = courseGenerator.getLastGeneratedCourse();
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("Failed to load course: " + e.getMessage()));
        }
    }
}

class ErrorResponse {
    private String error;
    
    public ErrorResponse(String error) {
        this.error = error;
    }
    
    public String getError() {
        return error;
    }
} 