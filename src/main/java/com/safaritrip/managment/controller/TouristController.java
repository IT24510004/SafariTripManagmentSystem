package com.safaritrip.management.controller;

import com.safaritrip.management.dto.ProfileDto;       // your own DTO
import com.safaritrip.management.service.UserService; // your own Service

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/tourist")
public class TouristController {

    @Autowired
    private UserService userService;
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

    
}