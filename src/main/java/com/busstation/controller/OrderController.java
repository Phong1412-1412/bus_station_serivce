package com.busstation.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.busstation.entities.Order;
import com.busstation.entities.User;
import com.busstation.event.SubmitOrderCompleteEvent;
import com.busstation.payload.request.OrderDetailRequest;
import com.busstation.payload.response.MyBookingResponse;
import com.busstation.payload.response.OrderDetailResponse;
import com.busstation.repositories.OrderRepository;
import com.busstation.repositories.UserRepository;
import com.busstation.services.OrderService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController(value = "orderAPIofWeb")
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/search/{order_id}")
    public ResponseEntity<?> searchOrderById(@PathVariable("order_id") String orderId,
                                             @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<OrderDetailResponse> orderDetailPage = orderService.searchOrderById(orderId, pageNo, pageSize);

        return new ResponseEntity<>(orderDetailPage, HttpStatus.OK);
    }

    @GetMapping("/findCancel/{orderId}")
    public ResponseEntity<?> cancellationCount(@PathVariable(name = "orderId") String orderId) {
        return ResponseEntity.ok().body(orderRepository.findCancellationCountForUserByOrderId(orderId));
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitOrder(@RequestBody OrderDetailRequest orderDetailRequest) {

        Boolean orderResponse = orderService.submitOrder(orderDetailRequest.getOrderId(), orderDetailRequest.getTripId(), orderDetailRequest.getPaymentId());
        if(orderResponse){
            return new ResponseEntity<>("successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("failed", HttpStatus.OK);
    }
    
    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMail(@RequestBody OrderDetailRequest orderDetailRequest) {
            User user = userRepository.findUserByOrderID(orderDetailRequest.getOrderId());
            Order order = orderRepository.findById(orderDetailRequest.getOrderId()).orElse(null);
            publisher.publishEvent(new SubmitOrderCompleteEvent(user, order));
            return new ResponseEntity<>("successfully", HttpStatus.OK);
    }

    @DeleteMapping("/cancellingInvoice/{orderId}")
    public ResponseEntity<?> cancellingInvoice(@PathVariable("orderId") String orderId) {

        Boolean orderResponse =orderService.deleteOrder(orderId);
        if(orderResponse)
            return new ResponseEntity<>("successfully", HttpStatus.OK);

        return new ResponseEntity<>("failed", HttpStatus.OK);
    }

    @GetMapping("/verify-order-info/{orderId}")
    public ResponseEntity<?> getInformationOrder(@PathVariable String orderId) {

        MyBookingResponse order = orderService.getInformationNewOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/countOrderByPaymentAtStation")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DRIVER')")
    public ResponseEntity<?> countOrderByPaymentAtStation() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = new User();
        if(userEmail != null) {
             user = userRepository.findByEmail(userEmail);
             return ResponseEntity.ok().body(orderService.getOrderByTripAndUser(user.getUserId()));
        }
       return ResponseEntity.ok().body("User not found!!!");
    }

}
