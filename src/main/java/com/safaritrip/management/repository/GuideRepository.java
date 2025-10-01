package com.safaritrip.management.repository;

import com.safaritrip.management.model.Guide;
import com.safaritrip.management.model.User; // Import User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import Optional

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    // New method to find a guide by their user account
    Optional<Guide> findByUserAccount(User user);
}