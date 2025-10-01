package com.safaritrip.management.service;

import com.safaritrip.management.dto.FeedbackDto;
import com.safaritrip.management.model.Feedback; // Import Feedback
import java.util.List; // Import List

public interface FeedbackService {
    void saveFeedback(FeedbackDto feedbackDto, String username);

    // Add these new methods
    List<Feedback> findAllFeedback();
    void saveAdminReply(Long feedbackId, String reply);
}