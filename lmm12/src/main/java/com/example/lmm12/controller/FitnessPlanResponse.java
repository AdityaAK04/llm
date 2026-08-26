package com.example.lmm12.controller;

import java.util.List;

public record FitnessPlanResponse(
        List<DailyPlan> dailyPlans
) {
    public record DailyPlan(
            String day,
            String activityName,
            String duration,
            String goal,
            String dietaryNotes
    ) {}
}