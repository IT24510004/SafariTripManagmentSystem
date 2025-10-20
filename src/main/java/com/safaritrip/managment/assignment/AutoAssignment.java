package com.safaritrip.management.assignment;


import com.safaritrip.management.model.AssignmentRequest;


import org.springframework.stereotype.Component;

@Component("auto")
public class AutoAssignment implements AssignmentStrategy {

    @Override
    public AssignmentResult assign(AssignmentRequest request) {
        AssignmentResult res = new AssignmentResult();
        res.setGuideId("G" + Math.round(Math.random() * 100));
        res.setVehicleId("V" + Math.round(Math.random() * 100));
        res.setSuccess(true);
        res.setMessage("Auto-assigned guide and vehicle successfully.");
        return res;
    }

    @Override
    public String getName() {
        return "Auto";
    }
}
