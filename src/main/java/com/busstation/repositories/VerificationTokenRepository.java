package com.busstation.repositories;

import com.busstation.controller.verifyToken.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
