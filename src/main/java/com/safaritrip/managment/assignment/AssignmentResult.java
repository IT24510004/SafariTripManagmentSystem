package com.safaritrip.management.assignment;

public class AssignmentResult {
    private boolean success;
    private String guideId;
    private String vehicleId;
    private String message;

    // --- Getters and Setters ---
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getGuideId() {
        return guideId;
    }
    public void setGuideId(String guideId) {
        this.guideId = guideId;
    }

    public String getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
