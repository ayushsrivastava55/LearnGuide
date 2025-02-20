package com.learnguide.model;

import lombok.Data;
import java.util.UUID;

@Data
public class Resource {
    private String resourceId;
    private String title;
    private String url;
    private ResourceType type;
    
    public Resource() {
        this.resourceId = UUID.randomUUID().toString();
    }
} 