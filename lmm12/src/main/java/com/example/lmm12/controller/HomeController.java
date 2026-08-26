package com.example.lmm12.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final ChatClient chatClient;

    public HomeController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("You are a supportive fitness coach. Provide structured fitness plans.")
                .build();
    }

    // Exercise 2 Endpoint
    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    // Exercise 3 Endpoint with Prompt Template
    @GetMapping("/personalized-plan")
    public String getPersonalizedPlan(
            @RequestParam String goal,
            @RequestParam(required = false, defaultValue = "None") String diet,
            @RequestParam(required = false, defaultValue = "Not specified") String duration) {

        String promptTemplate = "Create a personalized plan based on the following inputs:\n" +
                "- Fitness Goal: {goal}\n" +
                "- Dietary Preference (if any): {diet}\n" +
                "- Duration (if any): {duration}\n" +
                "Return the response in 3–5 concise bullet points with clear, practical advice.";

        return chatClient.prompt()
                .user(u -> u.text(promptTemplate)
                        .param("goal", goal)
                        .param("diet", diet)
                        .param("duration", duration))
                .call()
                .content();
    }

    // Exercise 4 Endpoint returning a structured Java Object
    @GetMapping("/structured-plan")
    public FitnessPlanResponse getStructuredPlan(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .entity(FitnessPlanResponse.class);
    }
}