package com.safaritrip.management.service;

import com.safaritrip.management.dto.ProfileDto;
import com.safaritrip.management.dto.UserDto;
import com.safaritrip.management.model.User;

import java.util.List; // Import List

public interface UserService {
    void saveUser(UserDto userDto);
    User findByUsername(String username);
    void updateUserProfile(String username, ProfileDto profileDto);

    // Add this new method
    List<User> findAllTourists();
}