package com.learnguide.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserProgress {
    private String userId;
    private Map<String, Double> courseMasteryScores;
    private Map<String, List<String>> completedModules;

    public UserProgress(String userId) {
        this.userId = userId;
        this.courseMasteryScores = new HashMap<>();
        this.completedModules = new HashMap<>();
    }

    public void markModuleComplete(String courseId, String moduleId) {
        completedModules.computeIfAbsent(courseId, k -> new ArrayList<>()).add(moduleId);
    }

    public double calculateMasteryScore(String courseId) {
        // Simple implementation - can be enhanced based on requirements
        return completedModules.containsKey(courseId) ? 
            (completedModules.get(courseId).size() * 100.0) : 0.0;
    }

    public void updateMasteryScore(String courseId, double score) {
        courseMasteryScores.put(courseId, score);
    }
} 