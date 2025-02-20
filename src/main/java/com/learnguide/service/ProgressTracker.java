package com.learnguide.service;

import com.learnguide.model.*;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProgressTracker {
    private Map<String, UserProgress> userProgress = new HashMap<>();

    public void updateProgress(User user, Course course, com.learnguide.model.Module completedModule) {
        UserProgress progress = userProgress.getOrDefault(
            user.getUserId(), 
            new UserProgress(user.getUserId())
        );
        
        progress.markModuleComplete(course.getCourseId(), completedModule.getModuleId());
        calculateAndUpdateMastery(user, course, progress);
    }

    private void calculateAndUpdateMastery(User user, Course course, UserProgress progress) {
        double masteryScore = progress.calculateMasteryScore(course.getCourseId());
        progress.updateMasteryScore(course.getCourseId(), masteryScore);
    }
} 