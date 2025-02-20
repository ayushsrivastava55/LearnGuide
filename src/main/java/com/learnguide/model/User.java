package com.learnguide.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User {
    private String userId;
    private String username;
    private String email;
    private String password;
    private List<Course> enrolledCourses;
    private LearningPreferences preferences;

    public User(String username, String email, String password) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.password = password;
        this.enrolledCourses = new ArrayList<>();
        this.preferences = new LearningPreferences();
    }

    public String getUserId() {
        return userId;
    }

    public LearningPreferences getPreferences() {
        return preferences;
    }

    public void enrollInCourse(Course course) {
        enrolledCourses.add(course);
    }
} 