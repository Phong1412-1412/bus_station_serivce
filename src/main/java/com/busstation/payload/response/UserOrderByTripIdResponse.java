package com.busstation.payload.response;

import com.busstation.entities.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOrderByTripIdResponse {
    private UserResponse userResponse;
    private PaymentMethod paymentMethod;
    private BigDecimal toTalPrice;
    private String chairNumber;
}
