package com.learnguide.dto;

import com.learnguide.model.DifficultyLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseGenerationRequest {
    private String userId;
    private String topic;
    private DifficultyLevel difficulty;

    public String getUserId() {
        return userId;
    }

    public String getTopic() {
        return topic;
    }
} 