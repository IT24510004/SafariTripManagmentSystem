package com.safaritrip.management.assignment;


import com.safaritrip.management.model.AssignmentRequest;

public interface AssignmentStrategy {
    AssignmentResult assign(AssignmentRequest request);
    String getName();
}
