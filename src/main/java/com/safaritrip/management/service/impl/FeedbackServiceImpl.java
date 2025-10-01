package com.safaritrip.management.service.impl;

import com.safaritrip.management.dto.FeedbackDto;
import com.safaritrip.management.model.Booking;
import com.safaritrip.management.model.Feedback;
import com.safaritrip.management.model.User;
import com.safaritrip.management.repository.BookingRepository;
import com.safaritrip.management.repository.FeedbackRepository;
import com.safaritrip.management.repository.UserRepository;
import com.safaritrip.management.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List; // Import List

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void saveFeedback(FeedbackDto feedbackDto, String username) {
        User tourist = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = bookingRepository.findById(feedbackDto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Feedback feedback = new Feedback();
        feedback.setBooking(booking);
        feedback.setTourist(tourist);
        feedback.setRating(feedbackDto.getRating());
        feedback.setReview(feedbackDto.getReview());

        feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public void saveAdminReply(Long feedbackId, String reply) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        feedback.setAdminReply(reply);
        feedbackRepository.save(feedback);
    }
}