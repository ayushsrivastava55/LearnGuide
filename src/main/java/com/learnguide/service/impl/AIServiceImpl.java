package com.learnguide.service.impl;

import com.learnguide.model.*;
import com.learnguide.service.AIService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service("fallbackAIService")
public class AIServiceImpl implements AIService {
    @Override
    public Course generateCourseStructure(String topic, LearningPreferences preferences) {
        // Simple implementation for demonstration
        Course course = new Course(
            "Introduction to " + topic,
            "A comprehensive course about " + topic,
            preferences.getPreferredDifficulty()
        );

        // Add some sample modules
        com.learnguide.model.Module module1 = new com.learnguide.model.Module();
        module1.setTopic("Basics of " + topic);
        module1.setDuration(60); // 60 minutes
        module1.setDifficulty(DifficultyLevel.BEGINNER);

        com.learnguide.model.Module module2 = new com.learnguide.model.Module();
        module2.setTopic("Advanced " + topic);
        module2.setDuration(90);
        module2.setDifficulty(DifficultyLevel.INTERMEDIATE);

        course.addModule(module1);
        course.addModule(module2);

        return course;
    }
} 