package com.example.llmmemory.controller;

import com.example.llmmemory.tool.CalorieCalculatorTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatControllerTools {

    private final ChatClient chatClient;
    private final CalorieCalculatorTool calorieCalculatorTool;

    public ChatControllerTools(ChatClient.Builder builder, CalorieCalculatorTool calorieCalculatorTool) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(10)
                .build();

        this.chatClient = builder
                .defaultSystem("You are a helpful fitness and nutrition assistant.")
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        this.calorieCalculatorTool = calorieCalculatorTool;
    }

   
    // Endpoint 2: Direct tool lookup endpoint matching "Calories in 2 boiled eggs?"
    @GetMapping("/api/calories-query")
    public String queryCalories(@RequestParam String message) {
        String lower = message.toLowerCase();

        if (lower.contains("boiled egg") || lower.contains("egg")) {
            return calorieCalculatorTool.calculateCalories("boiled egg", 2);
        } else if (lower.contains("banana")) {
            return calorieCalculatorTool.calculateCalories("banana", 2);
        } else if (lower.contains("apple")) {
            return calorieCalculatorTool.calculateCalories("apple", 1);
        }

        // Fallback to chat if tool item isn't directly caught
        return chatClient.prompt().user(message).call().content();
    }
}