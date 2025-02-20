package com.learnguide.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class LearningPreferences {
    private int dailyStudyHours;
    private List<String> preferredTopics;
    private DifficultyLevel preferredDifficulty;
    private LearningStyle learningStyle;

    public LearningPreferences() {
        this.preferredTopics = new ArrayList<>();
        this.dailyStudyHours = 2; // default value
        this.preferredDifficulty = DifficultyLevel.INTERMEDIATE;
        this.learningStyle = LearningStyle.VISUAL;
    }

    public DifficultyLevel getPreferredDifficulty() {
        return preferredDifficulty;
    }
} 