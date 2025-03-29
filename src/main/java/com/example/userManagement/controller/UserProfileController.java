package com.example.userManagement.controller;

import com.example.userManagement.dto.EmailRequestDTO;
import com.example.userManagement.dto.UserProfileDTO;
import com.example.userManagement.model.ProviderProfile;
import com.example.userManagement.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/details/{userID}") //throws details from the user-profile table 
    /*
     * in some services we need the id from the user-profile so i (vaibhavkumar Patel) created this service
     */
    public ResponseEntity<?> getUserDetails(@PathVariable String userID) {
        UserProfileDTO userProfileDTO = userProfileService.getUserDetails(userID);
        return ResponseEntity.ok(userProfileDTO);
    }

    @GetMapping
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile() {
        return ResponseEntity.ok(userProfileService.getCurrentUserProfile());
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileDTO profileDTO) {
        return ResponseEntity.ok(userProfileService.updateProfile(profileDTO));
    }

    @PostMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(@RequestBody EmailRequestDTO request) {
        return userProfileService.getUserInfo(request.getEmail());
    }
}