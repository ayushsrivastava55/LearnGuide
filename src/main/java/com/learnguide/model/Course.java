package com.learnguide.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Course {
    private String courseId;
    private String title;
    private String description;
    private List<Module> modules;
    private int estimatedDuration; // in hours
    private DifficultyLevel difficulty;
    
    public Course(String title, String description, DifficultyLevel difficulty) {
        this.courseId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.modules = new ArrayList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void addModule(Module module) {
        modules.add(module);
        updateEstimatedDuration();
    }

    private void updateEstimatedDuration() {
        this.estimatedDuration = modules.stream()
            .mapToInt(Module::getDuration)
            .sum();
    }
} 