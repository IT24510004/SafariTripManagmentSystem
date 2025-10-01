package com.safaritrip.management.repository;

import com.safaritrip.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA will automatically create a query for us based on the method name
    Optional<User> findByUsername(String username);
}