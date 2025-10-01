package com.safaritrip.management.controller;

import com.safaritrip.management.dto.BookingDto;
import com.safaritrip.management.dto.FeedbackDto;
import com.safaritrip.management.dto.ProfileDto;
import com.safaritrip.management.model.Booking;
import com.safaritrip.management.model.BookingStatus;
import com.safaritrip.management.model.NationalPark;
import com.safaritrip.management.model.User;
import com.safaritrip.management.repository.BookingRepository;
import com.safaritrip.management.repository.NationalParkRepository;
import com.safaritrip.management.service.BookingService;
import com.safaritrip.management.service.FeedbackService;
import com.safaritrip.management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tourist")
public class TouristController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private NationalParkRepository nationalParkRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/profile")
    public String showProfilePage(Model model, Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());
        ProfileDto profileDto = new ProfileDto();
        profileDto.setEmail(currentUser.getEmail());
        profileDto.setPhoneNumber(currentUser.getPhoneNumber());
        model.addAttribute("profile", profileDto);
        return "tourist/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("profile") @Valid ProfileDto profileDto, BindingResult result, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) { return "tourist/profile"; }
        userService.updateUserProfile(authentication.getName(), profileDto);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/tourist/profile";
    }

    @GetMapping("/booking")
    public String showBookingPage(Model model) {
        List<NationalPark> parks = nationalParkRepository.findAll();
        model.addAttribute("parks", parks);
        model.addAttribute("booking", new BookingDto());
        return "tourist/booking";
    }

    @PostMapping("/booking")
    public String processBooking(@ModelAttribute("booking") @Valid BookingDto bookingDto, BindingResult result, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<NationalPark> parks = nationalParkRepository.findAll();
            model.addAttribute("parks", parks);
            return "tourist/booking";
        }
        Booking newBooking = bookingService.createBooking(bookingDto, authentication.getName());
        redirectAttributes.addFlashAttribute("booking", newBooking);
        return "redirect:/tourist/booking-confirmation";
    }

    @GetMapping("/booking-confirmation")
    public String showBookingConfirmation(Model model) {
        if (!model.containsAttribute("booking")) { return "redirect:/tourist/booking"; }
        return "tourist/booking-confirmation";
    }

    @GetMapping("/bookings")
    public String showBookingHistory(Model model, Authentication authentication) {
        List<Booking> bookings = bookingService.findBookingsByUsername(authentication.getName());
        model.addAttribute("bookings", bookings);
        return "tourist/bookings";
    }

    @GetMapping("/feedback/new/{bookingId}")
    public String showFeedbackForm(@PathVariable("bookingId") Long bookingId, Model model) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setBookingId(bookingId);
        model.addAttribute("booking", booking);
        model.addAttribute("feedback", feedbackDto);
        return "tourist/add-feedback";
    }

    @PostMapping("/feedback/new")
    public String saveFeedback(@ModelAttribute("feedback") @Valid FeedbackDto feedbackDto,
                               BindingResult result,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Rating and review are required.");
            return "redirect:/tourist/feedback/new/" + feedbackDto.getBookingId();
        }
        feedbackService.saveFeedback(feedbackDto, authentication.getName());
        redirectAttributes.addFlashAttribute("successMessage", "Thank you for your feedback!");
        return "redirect:/tourist/bookings";
    }

    // NEW METHODS FOR PAYMENT
    @GetMapping("/booking/pay/{bookingId}")
    public String showPaymentPage(@PathVariable("bookingId") Long bookingId, Model model) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            return "redirect:/tourist/bookings";
        }
        model.addAttribute("booking", bookingOpt.get());
        return "tourist/payment";
    }

    @PostMapping("/booking/pay")
    public String processPayment(@RequestParam("bookingId") Long bookingId, RedirectAttributes redirectAttributes) {
        bookingService.updateBookingStatus(bookingId, BookingStatus.CONFIRMED);
        redirectAttributes.addFlashAttribute("successMessage", "Payment successful! Your booking is confirmed.");
        return "redirect:/tourist/bookings";
    }
}