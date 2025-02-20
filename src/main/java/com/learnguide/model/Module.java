package com.learnguide.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Getter
@Setter
public class Module {
    private String moduleId;
    private String topic;
    private String description;
    private int duration;
    private DifficultyLevel difficulty;
    private List<Resource> resources;
    private List<String> learningObjectives;
    private List<String> keyPoints;

    public Module() {
        this.moduleId = UUID.randomUUID().toString();
        this.learningObjectives = new ArrayList<>();
        this.keyPoints = new ArrayList<>();
    }

    // Explicitly add getters and setters that Lombok might miss
    public String getModuleId() {
        return moduleId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
} 