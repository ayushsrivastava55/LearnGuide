package com.learnguide.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnguide.model.Course;
import com.learnguide.model.DifficultyLevel;
import com.learnguide.model.LearningPreferences;
import com.learnguide.model.Resource;
import com.learnguide.model.ResourceType;
import com.learnguide.service.AIService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

@Service("openAIService")
@Primary
public class OpenAIServiceImpl implements AIService {

    @Autowired
    private OpenAiService openAiService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Course generateCourseStructure(String topic, LearningPreferences preferences) {
        try {
            String prompt = String.format(
                "Create a detailed course structure for '%s' at %s level. " +
                "The response should be in JSON format with this exact structure:\n" +
                "{\n" +
                "  \"title\": \"Descriptive course title\",\n" +
                "  \"description\": \"Comprehensive course description explaining learning outcomes\",\n" +
                "  \"modules\": [\n" +
                "    {\n" +
                "      \"topic\": \"Module specific topic\",\n" +
                "      \"description\": \"Detailed module description\",\n" +
                "      \"duration\": \"Duration in minutes (integer)\",\n" +
                "      \"difficulty\": \"BEGINNER or INTERMEDIATE or ADVANCED\",\n" +
                "      \"learningObjectives\": [\"objective1\", \"objective2\"],\n" +
                "      \"keyPoints\": [\"key point 1\", \"key point 2\"]\n" +
                "    }\n" +
                "  ]\n" +
                "}\n\n" +
                "Requirements:\n" +
                "1. Include 3-5 modules with progressive difficulty\n" +
                "2. Each module should build upon previous knowledge\n" +
                "3. Make content practical and hands-on\n" +
                "4. Include both theoretical and practical aspects\n" +
                "5. Target %s learning style\n" +
                "6. Ensure each module is 30-90 minutes long",
                topic,
                preferences.getPreferredDifficulty(),
                preferences.getLearningStyle()
            );

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(new ChatMessage("user", prompt)))
                .temperature(0.7)
                .maxTokens(1000)
                .build();

            String response = openAiService.createChatCompletion(request)
                .getChoices().get(0).getMessage().getContent();

            JsonNode jsonNode = objectMapper.readTree(response);

            Course course = new Course(
                jsonNode.get("title").asText(),
                jsonNode.get("description").asText(),
                preferences.getPreferredDifficulty()
            );

            JsonNode modules = jsonNode.get("modules");
            for (JsonNode moduleNode : modules) {
                com.learnguide.model.Module module = new com.learnguide.model.Module();
                module.setTopic(moduleNode.get("topic").asText());
                module.setDescription(moduleNode.get("description").asText());
                module.setDuration(moduleNode.get("duration").asInt());
                module.setDifficulty(DifficultyLevel.valueOf(moduleNode.get("difficulty").asText()));

                // Create resources based on learning objectives and key points
                List<Resource> resources = new ArrayList<>();
                
                // Add video resource
                resources.add(createResource(module.getTopic(), ResourceType.VIDEO, "Video: "));
                
                // Add article resource
                resources.add(createResource(module.getTopic(), ResourceType.ARTICLE, "Article: "));

                // Add quiz
                resources.add(createResource(module.getTopic(), ResourceType.QUIZ, "Quiz: Test Your "));

                // Add practical exercise
                resources.add(createResource(module.getTopic(), ResourceType.EXERCISE, "Practice: "));

                module.setResources(resources);
                course.addModule(module);
            }

            return course;
        } catch (Exception e) {
            if (e.getCause() instanceof java.net.SocketTimeoutException) {
                return createFallbackCourse(topic, preferences);
            }
            throw new RuntimeException("Failed to generate course structure: " + e.getMessage(), e);
        }
    }

    private Resource createResource(String topic, ResourceType type, String prefix) {
        Resource resource = new Resource();
        resource.setTitle(prefix + topic);
        resource.setType(type);
        resource.setUrl("https://example.com/" + type.name().toLowerCase() + "/" + topic.toLowerCase().replace(" ", "-"));
        return resource;
    }

    private Course createFallbackCourse(String topic, LearningPreferences preferences) {
        Course course = new Course(
            "Introduction to " + topic,
            "A comprehensive course covering the fundamentals of " + topic,
            preferences.getPreferredDifficulty()
        );

        // Create basic modules
        com.learnguide.model.Module basicModule = new com.learnguide.model.Module();
        basicModule.setTopic("Fundamentals of " + topic);
        basicModule.setDescription("Learn the basic concepts and principles of " + topic);
        basicModule.setDuration(60);
        basicModule.setDifficulty(DifficultyLevel.BEGINNER);
        
        // Add basic resources
        List<Resource> resources = new ArrayList<>();
        resources.add(createResource("Introduction to " + topic, ResourceType.VIDEO, "Introduction: "));

        basicModule.setResources(resources);
        course.addModule(basicModule);

        return course;
    }
}
