package com.busstation.entities;

import com.busstation.payload.request.PaymentMethodRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Payment_Method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentId")
    private Long id;

    @Column(name = "payment_method", unique = true)
    private String paymentMethod;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "paymentMethod")
    @JsonIgnore
    Set<Order> orders;
}
