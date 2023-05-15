package com.busstation.controller;

import com.busstation.entities.PaymentMethod;
import com.busstation.payload.request.PaymentMethodRequest;
import com.busstation.repositories.PaymentMethodRepository;
import com.busstation.services.impl.PaymentMethodServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentMethodController {
    private final PaymentMethodServiceImpl paymentMethodService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPaymentMethods() {
        return ResponseEntity.ok().body(paymentMethodService.getAllPaymentMethode());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodService.createPaymentMethod(paymentMethodRequest));
    }

    @PutMapping("/update/{paymentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable(name = "paymentId") Long paymentId,@RequestBody PaymentMethodRequest paymentMethodRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodService.updatePaymentMethod(paymentId ,paymentMethodRequest));
    }

    @DeleteMapping("/delete/{paymentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable(name = "paymentId") Long paymentId) {
        return ResponseEntity.ok().body(paymentMethodService.deletePaymentMethod(paymentId));
    }

}
