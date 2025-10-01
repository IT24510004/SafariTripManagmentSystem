package com.safaritrip.management.config;

import com.safaritrip.management.model.Guide; // Import Guide
import com.safaritrip.management.model.NationalPark;
import com.safaritrip.management.model.Role;
import com.safaritrip.management.model.User;
import com.safaritrip.management.repository.GuideRepository; // Import GuideRepository
import com.safaritrip.management.repository.NationalParkRepository;
import com.safaritrip.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private NationalParkRepository nationalParkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GuideRepository guideRepository; // Add GuideRepository
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedNationalParks();
        seedAdminUser();
        seedGuideUser(); // Call the new method
    }

    private void seedNationalParks() {
        if (nationalParkRepository.count() == 0) {
            // ... same as before
            NationalPark yala = new NationalPark();
            yala.setName("Yala National Park");
            nationalParkRepository.save(yala);
            NationalPark wilpattu = new NationalPark();
            wilpattu.setName("Wilpattu National Park");
            nationalParkRepository.save(wilpattu);
            NationalPark udawalawe = new NationalPark();
            udawalawe.setName("Udawalawe National Park");
            nationalParkRepository.save(udawalawe);
            System.out.println("Seeded National Parks data.");
        }
    }

    private void seedAdminUser() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            // ... same as before
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpass"));
            admin.setEmail("admin@safaritrip.com");
            admin.setPhoneNumber("0000000000");
            admin.setRoles(Set.of(Role.ADMIN));
            userRepository.save(admin);
            System.out.println("Seeded Admin user.");
        }
    }

    // New method to seed a guide
    private void seedGuideUser() {
        if (userRepository.findByUsername("guide1").isEmpty()) {
            // First, create the User account details
            User guideUser = new User();
            guideUser.setUsername("guide1");
            guideUser.setPassword(passwordEncoder.encode("guidepass"));
            guideUser.setEmail("guide1@safaritrip.com");
            guideUser.setPhoneNumber("1112223333");
            guideUser.setRoles(Set.of(Role.GUIDE));

            // Then, create the Guide resource details
            Guide guide = new Guide();
            guide.setName("John Carter");
            guide.setContactNumber("1112223333");
            guide.setLicenseNumber("G-789123");

            // Link the User account to the Guide resource
            guide.setUserAccount(guideUser);

            // Save the Guide (which will also save the User because of CascadeType.ALL)
            guideRepository.save(guide);
            System.out.println("Seeded Guide user.");
        }
    }
}