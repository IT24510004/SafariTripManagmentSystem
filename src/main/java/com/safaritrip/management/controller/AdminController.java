package com.safaritrip.management.controller;

import com.safaritrip.management.model.*;
import com.safaritrip.management.repository.BookingRepository; // Import BookingRepository
import com.safaritrip.management.repository.GuideRepository;
import com.safaritrip.management.repository.VehicleRepository;
import com.safaritrip.management.service.BookingService;
import com.safaritrip.management.service.FeedbackService;
import com.safaritrip.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private GuideRepository guideRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingRepository bookingRepository; // Add BookingRepository

    // ... all other methods are unchanged ...
    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "admin/dashboard";
    }

    @GetMapping("/bookings")
    public String listAllBookings(Model model) {
        List<Booking> bookings = bookingService.findAllBookings();
        model.addAttribute("bookings", bookings);
        model.addAttribute("statuses", BookingStatus.values());
        return "admin/bookings";
    }

    @PostMapping("/bookings/update-status")
    public String updateBookingStatus(@RequestParam("bookingId") Long bookingId,
                                      @RequestParam("status") BookingStatus status) {
        bookingService.updateBookingStatus(bookingId, status);
        return "redirect:/admin/bookings";
    }

    @GetMapping("/feedback")
    public String listAllFeedback(Model model) {
        List<Feedback> feedbackList = feedbackService.findAllFeedback();
        model.addAttribute("feedbackList", feedbackList);
        return "admin/feedback";
    }

    @PostMapping("/feedback/reply")
    public String saveAdminReply(@RequestParam("feedbackId") Long feedbackId,
                                 @RequestParam("reply") String reply) {
        feedbackService.saveAdminReply(feedbackId, reply);
        return "redirect:/admin/feedback";
    }

    @GetMapping("/tourists")
    public String listTourists(Model model) {
        model.addAttribute("tourists", userService.findAllTourists());
        return "admin/tourists";
    }

    @GetMapping("/guides")
    public String listGuides(Model model) {
        model.addAttribute("guides", guideRepository.findAll());
        return "admin/guides";
    }

    @GetMapping("/guides/new")
    public String showAddGuideForm(Model model) {
        model.addAttribute("guide", new Guide());
        return "admin/guide-form";
    }

    @PostMapping("/guides/save")
    public String saveGuide(@ModelAttribute("guide") Guide guide) {
        guideRepository.save(guide);
        return "redirect:/admin/guides";
    }

    @GetMapping("/guides/edit/{id}")
    public String showEditGuideForm(@PathVariable("id") Long id, Model model) {
        Guide guide = guideRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid guide Id:" + id));
        model.addAttribute("guide", guide);
        return "admin/guide-form";
    }

    @GetMapping("/guides/delete/{id}")
    public String deleteGuide(@PathVariable("id") Long id) {
        guideRepository.deleteById(id);
        return "redirect:/admin/guides";
    }

    @GetMapping("/vehicles")
    public String listVehicles(Model model) {
        model.addAttribute("vehicles", vehicleRepository.findAll());
        return "admin/vehicles";
    }

    @GetMapping("/vehicles/new")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "admin/vehicle-form";
    }

    @PostMapping("/vehicles/save")
    public String saveVehicle(@ModelAttribute("vehicle") Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        return "redirect:/admin/vehicles";
    }

    @GetMapping("/vehicles/edit/{id}")
    public String showEditVehicleForm(@PathVariable("id") Long id, Model model) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid vehicle Id:" + id));
        model.addAttribute("vehicle", vehicle);
        return "admin/vehicle-form";
    }

    @GetMapping("/vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable("id") Long id) {
        vehicleRepository.deleteById(id);
        return "redirect:/admin/vehicles";
    }

    @GetMapping("/bookings/assign/{id}")
    public String showAssignmentForm(@PathVariable("id") Long bookingId, Model model) {
        Booking booking = bookingService.findAllBookings().stream()
                .filter(b -> b.getId().equals(bookingId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        model.addAttribute("booking", booking);
        model.addAttribute("guides", guideRepository.findAll());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        return "admin/assign-form";
    }

    @PostMapping("/bookings/assign")
    public String saveAssignment(@RequestParam("bookingId") Long bookingId,
                                 @RequestParam("guideId") Long guideId,
                                 @RequestParam("vehicleId") Long vehicleId) {
        bookingService.assignGuideAndVehicle(bookingId, guideId, vehicleId);
        return "redirect:/admin/bookings";
    }

    // New method for the reports page
    @GetMapping("/reports")
    public String showReportsPage(Model model) {
        model.addAttribute("totalTourists", userService.findAllTourists().size());
        model.addAttribute("totalBookings", bookingRepository.count());
        model.addAttribute("pendingBookings", bookingRepository.countByStatus(BookingStatus.PENDING));
        model.addAttribute("confirmedBookings", bookingRepository.countByStatus(BookingStatus.CONFIRMED));
        model.addAttribute("completedBookings", bookingRepository.countByStatus(BookingStatus.COMPLETED));
        model.addAttribute("totalGuides", guideRepository.count());
        model.addAttribute("totalVehicles", vehicleRepository.count());
        return "admin/reports";
    }
}