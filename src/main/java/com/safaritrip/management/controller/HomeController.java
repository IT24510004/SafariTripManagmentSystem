package com.safaritrip.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "tourist"; // This should match your tourist.html filename
    }

    @GetMapping("/dashboard")
    public String touristDashboard() {
        return "tourist"; // tourist.html
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard"; // admin-dashboard.html
    }

    @GetMapping("/guide/schedule")
    public String guideSchedule() {
        return "guide-schedule"; // guide-schedule.html
    }

    @GetMapping("/vehicles")
    public String vehicles() {
        return "vehicles"; // vehicles.html
    }

    @GetMapping("/assign-form")
    public String assignForm() {
        return "assign-form"; // assign-form.html
    }

    @GetMapping("/bookings")
    public String bookings() {
        return "bookings"; // bookings.html
    }
}