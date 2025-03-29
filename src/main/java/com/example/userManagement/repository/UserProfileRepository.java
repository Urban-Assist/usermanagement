package com.example.userManagement.repository;

import com.example.userManagement.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    boolean existsByEmail(String email);
    Optional<UserProfile> findByEmail(String email);


    // Case-insensitive email lookup
    @Query("SELECT u FROM UserProfile u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<UserProfile> findByEmailNew(@Param("email") String email);
    
}
