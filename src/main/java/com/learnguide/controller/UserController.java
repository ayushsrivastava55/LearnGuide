package com.learnguide.controller;

import com.learnguide.model.User;
import com.learnguide.model.LearningPreferences;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // In real app, would hash password and save to database
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}/preferences")
    public ResponseEntity<LearningPreferences> updatePreferences(
            @PathVariable String userId,
            @RequestBody LearningPreferences preferences) {
        // Update user preferences
        return ResponseEntity.ok(preferences);
    }
} 