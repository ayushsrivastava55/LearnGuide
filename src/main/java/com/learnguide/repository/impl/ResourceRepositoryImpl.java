package com.learnguide.repository.impl;

import com.learnguide.model.Resource;
import com.learnguide.model.ResourceType;
import com.learnguide.model.DifficultyLevel;
import com.learnguide.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ResourceRepositoryImpl implements ResourceRepository {
    @Override
    public List<Resource> findRelevantResources(String topic, DifficultyLevel difficulty) {
        // Mock implementation - in real app, this would fetch from database
        List<Resource> resources = new ArrayList<>();
        
        // Add some sample resources
        Resource videoResource = new Resource();
        videoResource.setTitle("Introduction to " + topic);
        videoResource.setType(ResourceType.VIDEO);
        videoResource.setUrl("https://example.com/video/" + topic.toLowerCase());
        
        Resource articleResource = new Resource();
        articleResource.setTitle(topic + " Fundamentals");
        articleResource.setType(ResourceType.ARTICLE);
        articleResource.setUrl("https://example.com/article/" + topic.toLowerCase());
        
        resources.add(videoResource);
        resources.add(articleResource);
        
        return resources;
    }
} 