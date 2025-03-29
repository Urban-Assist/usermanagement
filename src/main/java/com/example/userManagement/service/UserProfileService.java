package com.example.userManagement.service;

import com.example.userManagement.dto.UserProfileDTO;
import com.example.userManagement.model.ProviderProfile;
import com.example.userManagement.model.UserProfile;
import com.example.userManagement.repository.ProviderProfileRepository;
import com.example.userManagement.repository.UserProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final ProviderProfileRepository providerProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, 
                              ProviderProfileRepository providerProfileRepository) {
        this.userProfileRepository = userProfileRepository;
        this.providerProfileRepository = providerProfileRepository;
    }

    public UserProfileDTO getCurrentUserProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserProfile userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return convertToDTO(userProfile);
    }

    public UserProfileDTO updateProfile(UserProfileDTO profileDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!email.equals(profileDTO.getEmail())) {
            throw new RuntimeException("Cannot update other user's profile");
        }
    
        // Fetch existing user profile
        UserProfile existingUserProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    
        // Update the existing profile
        BeanUtils.copyProperties(profileDTO, existingUserProfile, "id", "email");  // Don't overwrite id and email
    
        existingUserProfile = userProfileRepository.save(existingUserProfile);  // Save the updated profile
        return convertToDTO(existingUserProfile);
    }
    

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleUserRegistration(UserProfileDTO profileDTO) {
        if (!userProfileRepository.existsByEmail(profileDTO.getEmail())) {
            UserProfile profile = new UserProfile();
            profile.setEmail(profileDTO.getEmail());
            profile.setFirstName(profileDTO.getFirstName());
            profile.setLastName(profileDTO.getLastName());
            profile.setRole(profileDTO.getRole());

            userProfileRepository.save(profile);
            System.out.println("User profile created for: " + profileDTO.getEmail() + " ✅");
        } else {
            System.out.println("Profile already exists for: " + profileDTO.getEmail());
        }
    }


    public  ResponseEntity<?> getUserDetails(String userID) {
        Long userIdLong = Long.parseLong(userID);
        ProviderProfile userProfile = providerProfileRepository.findById(userIdLong)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return ResponseEntity.ok(userProfile);
    }
    private UserProfileDTO convertToDTO(UserProfile userProfile) {
        UserProfileDTO dto = new UserProfileDTO();
        BeanUtils.copyProperties(userProfile, dto);
        return dto;
    }
}