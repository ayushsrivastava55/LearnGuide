package com.learnguide.service;

import com.learnguide.model.*;
import com.learnguide.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseGenerator {
    private final AIService aiService;
    private final ResourceRepository resourceRepo;
    private Course lastGeneratedCourse;

    @Autowired
    public CourseGenerator(AIService aiService, ResourceRepository resourceRepo) {
        this.aiService = aiService;
        this.resourceRepo = resourceRepo;
    }

    public Course generateCustomizedCourse(String topic, LearningPreferences preferences) {
        Course customCourse = aiService.generateCourseStructure(topic, preferences);
        this.lastGeneratedCourse = customCourse;
        
        populateWithResources(customCourse);
        
        return customCourse;
    }

    public Course getLastGeneratedCourse() {
        if (lastGeneratedCourse == null) {
            throw new RuntimeException("No course has been generated yet");
        }
        return lastGeneratedCourse;
    }

    private void populateWithResources(Course course) {
        for (com.learnguide.model.Module module : course.getModules()) {
            List<Resource> resources = resourceRepo.findRelevantResources(
                module.getTopic(),
                module.getDifficulty()
            );
            module.setResources(resources);
        }
    }
} 