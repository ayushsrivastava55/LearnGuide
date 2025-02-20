package com.learnguide.controller;

import com.learnguide.service.ProgressTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private ProgressTracker progressTracker;

    @GetMapping("/{userId}/{courseId}")
    public ResponseEntity<Map<String, Object>> getProgress(
            @PathVariable String userId,
            @PathVariable String courseId) {
        // Return progress data
        Map<String, Object> progress = Map.of(
            "completed", 75,
            "totalModules", 10,
            "completedModules", 7,
            "masteryScore", 80.5
        );
        return ResponseEntity.ok(progress);
    }
} 