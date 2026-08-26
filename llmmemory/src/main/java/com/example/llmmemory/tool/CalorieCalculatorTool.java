package com.example.llmmemory.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class CalorieCalculatorTool {

    private static final Map<String, Integer> calorieChart = new HashMap<>();

    static {
        calorieChart.put("boiled egg", 78);
        calorieChart.put("egg", 78);
        calorieChart.put("banana", 105);
        calorieChart.put("apple", 95);
        calorieChart.put("chicken breast", 165);
        calorieChart.put("rice (1 cup)", 200);
        calorieChart.put("oats (1 cup)", 150);
    }

    @Tool(description = "Calculate total calories for a given food item and quantity based on a predefined chart.")
    public String calculateCalories(String foodItem, int quantity) {
        String key = foodItem.toLowerCase().trim();
        if (calorieChart.containsKey(key)) {
            int caloriesPerUnit = calorieChart.get(key);
            int totalCalories = caloriesPerUnit * quantity;
            return "Calories in " + quantity + " " + foodItem + "(s): " + totalCalories + " kcal (" + caloriesPerUnit + " kcal each).";
        } else {
            return "Sorry, I don't have calorie information for '" + foodItem + "' in my chart. Try asking for items like boiled eggs, bananas, apples, or chicken breast!";
        }
    }
}