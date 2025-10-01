package com.safaritrip.management.service.impl;

import com.safaritrip.management.dto.ProfileDto;
import com.safaritrip.management.dto.UserDto;
import com.safaritrip.management.model.Role;
import com.safaritrip.management.model.User;
import com.safaritrip.management.repository.UserRepository;
import com.safaritrip.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List; // Import List
import java.util.Set;
import java.util.stream.Collectors; // Import Collectors

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Set.of(Role.TOURIST));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void updateUserProfile(String username, ProfileDto profileDto) {
        User user = findByUsername(username);
        if (user != null) {
            user.setEmail(profileDto.getEmail());
            user.setPhoneNumber(profileDto.getPhoneNumber());
            userRepository.save(user);
        }
    }

    @Override
    public List<User> findAllTourists() {
        // We find all users, then filter the list to keep only those
        // who have the TOURIST role.
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(Role.TOURIST))
                .collect(Collectors.toList());
    }
}