package com.safaritrip.management.controller;

import com.safaritrip.management.dto.UserDto;
import com.safaritrip.management.model.User;
import com.safaritrip.management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Handler to show the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // We add an empty UserDto object to the model to bind form data
        model.addAttribute("user", new UserDto());
        return "register";
    }

    // Handler to process registration form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto,
                               BindingResult result,
                               Model model) {

        // Check if username already exists
        User existingUser = userService.findByUsername(userDto.getUsername());
        if (existingUser != null) {
            result.rejectValue("username", null, "Username already exists");
        }

        // Check if passwords match
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "Passwords do not match");
        }

        // If there are validation errors, return to the registration form
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        // If everything is valid, save the user
        userService.saveUser(userDto);

        // Redirect to the login page with a success message
        return "redirect:/login?success";
    }
}