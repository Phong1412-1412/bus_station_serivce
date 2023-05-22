package com.busstation.repositories;

import com.busstation.controller.verifyToken.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    @Query("SELECT token FROM VerificationToken token WHERE token.token = :token")
    VerificationToken findByToken(@Param("token") String token);
    VerificationToken findByUser_UserId(String userId);
}
