package com.busstation.services.impl;

import com.busstation.entities.PaymentMethod;
import com.busstation.exception.DataExistException;
import com.busstation.exception.DataNotFoundException;
import com.busstation.payload.request.PaymentMethodRequest;
import com.busstation.repositories.PaymentMethodRepository;
import com.busstation.services.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    @Override
    public List<PaymentMethod> getAllPaymentMethode() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        if(!paymentMethods.isEmpty()) {
            return paymentMethods;
        }
        throw new DataNotFoundException("Can't find any payment method!");
    }

    @Override
    public PaymentMethod createPaymentMethod(PaymentMethodRequest paymentMethod) {
        Optional<PaymentMethod> paymentMethodExists = Optional.ofNullable(paymentMethodRepository.findByPaymentMethod(paymentMethod.getPaymentMethod()));
        if(paymentMethodExists.isPresent()) {
            throw new DataExistException("This payment method is exists. Please change other payment methods");
        }
        PaymentMethod newPaymentMethod = new PaymentMethod();
        newPaymentMethod.setPaymentMethod(paymentMethod.getPaymentMethod());
        newPaymentMethod.setDescription(paymentMethod.getDescription());
        return paymentMethodRepository.save(newPaymentMethod);
    }

    @Override
    public PaymentMethod updatePaymentMethod(Long paymentId, PaymentMethodRequest paymentMethod) {
        Optional<PaymentMethod> paymentMethodUpdate = paymentMethodRepository.findPaymentMethodById(paymentId);
        if(paymentMethodUpdate.isPresent()) {
            paymentMethodUpdate.get().setPaymentMethod(paymentMethod.getPaymentMethod());
            paymentMethodUpdate.get().setDescription(paymentMethod.getDescription());
            return paymentMethodRepository.save(paymentMethodUpdate.get());
        }
       throw new DataNotFoundException("Can't find any payment method");
    }

    @Override
    public String deletePaymentMethod(Long paymentId) {
        Optional<PaymentMethod> paymentMethodDelete = paymentMethodRepository.findPaymentMethodById(paymentId);
        if(paymentMethodDelete.isPresent()) {
            paymentMethodRepository.delete(paymentMethodDelete.get());
            return "Delete this payment method have name: "+ paymentMethodDelete.get().getPaymentMethod()+" successfully";
        }
        throw new DataNotFoundException("Payment method doesn't exists");
    }
}
