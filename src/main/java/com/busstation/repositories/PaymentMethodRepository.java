package com.busstation.repositories;

import com.busstation.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    @Query("SELECT pm FROM PaymentMethod pm where pm.id = :paymentId")
    Optional<PaymentMethod> findPaymentMethodById(@Param("paymentId") Long paymentId);

    PaymentMethod findByPaymentMethod(String paymentMethod);
}
