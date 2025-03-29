package com.example.userManagement.repository;

import com.example.userManagement.model.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {
    boolean existsByEmail(String email);

    // Add custom query with logging capability
    @Query("SELECT p FROM ProviderProfile p WHERE p.email = :email")
    Optional<ProviderProfile> findByEmail(@Param("email") String email);

    Optional<ProviderProfile> findByEmailAndService(String email, String service);

    List<ProviderProfile> findByService(String service);

    Optional<ProviderProfile> findByIdAndService(Long id, String service);

    List<ProviderProfile> findByCertifiedFalseOrCertifiedIsNull();

    List<ProviderProfile> findByCertifiedTrue();

    List<ProviderProfile> findByCertified(boolean certified);
}

