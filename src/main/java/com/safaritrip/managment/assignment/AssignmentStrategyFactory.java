package com.safaritrip.management.assignment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class AssignmentStrategyFactory {

    private final Map<String, AssignmentStrategy> strategies;

    @Autowired
    public AssignmentStrategyFactory(Map<String, AssignmentStrategy> strategies) {
        this.strategies = strategies;
    }

    public AssignmentStrategy getStrategy(String type) {
        AssignmentStrategy s = strategies.get(type);
        if (s == null) throw new IllegalArgumentException("Unknown assignment strategy: " + type);
        return s;
    }
}
