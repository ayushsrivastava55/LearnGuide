package com.learnguide.repository;

import com.learnguide.model.Resource;
import com.learnguide.model.DifficultyLevel;
import java.util.List;

public interface ResourceRepository {
    List<Resource> findRelevantResources(String topic, DifficultyLevel difficulty);
} 