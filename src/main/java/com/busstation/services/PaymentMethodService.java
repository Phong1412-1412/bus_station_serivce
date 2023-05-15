package com.busstation.services;

import com.busstation.entities.PaymentMethod;
import com.busstation.payload.request.PaymentMethodRequest;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {
    List<PaymentMethod> getAllPaymentMethode();

    PaymentMethod createPaymentMethod(PaymentMethodRequest paymentMethod);

    PaymentMethod updatePaymentMethod(Long paymentId, PaymentMethodRequest paymentMethod);

    String deletePaymentMethod(Long paymentId);
}
