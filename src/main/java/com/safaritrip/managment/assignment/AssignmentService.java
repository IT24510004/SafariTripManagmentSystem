package com.safaritrip.management.assignment;


import com.safaritrip.management.model.AssignmentRequest;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {

    private final AssignmentStrategyFactory factory;

    public AssignmentService(AssignmentStrategyFactory factory) {
        this.factory = factory;
    }

    public AssignmentResult assign(String type, AssignmentRequest request) {
        AssignmentStrategy strategy = factory.getStrategy(type);
        return strategy.assign(request);
    }
}
