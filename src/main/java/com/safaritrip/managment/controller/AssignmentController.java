package com.safaritrip.management.controller;


import com.safaritrip.management.assignment.AssignmentResult;
import com.safaritrip.management.assignment.AssignmentService;
import com.safaritrip.management.model.AssignmentRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @PostMapping("/assign")
    public AssignmentResult assign(@RequestParam String type,
                                   @RequestBody AssignmentRequest request) {
        return service.assign(type, request);
    }
}
