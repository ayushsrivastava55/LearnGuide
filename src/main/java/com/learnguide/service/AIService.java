package com.learnguide.service;

import com.learnguide.model.Course;
import com.learnguide.model.LearningPreferences;

public interface AIService {
    Course generateCourseStructure(String topic, LearningPreferences preferences);
} 