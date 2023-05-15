package com.busstation.controller;

import com.busstation.payload.request.OrderDetailRequest;
import com.busstation.payload.response.MyBookingResponse;
import com.busstation.payload.response.OrderDetailResponse;
import com.busstation.repositories.OrderDetailRepository;
import com.busstation.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController(value = "orderDetailAPIofWeb")
@RequestMapping("/api/v1/orderdetails")
@RequiredArgsConstructor
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    private final OrderDetailRepository orderDetailRepository;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_ADMIN')")
    public ResponseEntity<?> getAllOrderDetail(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<OrderDetailResponse> orderDetailPage = orderDetailService.getAllOrderDetail(pageNo, pageSize);
        return new ResponseEntity<>(orderDetailPage, HttpStatus.OK);
    }

    @GetMapping("/count/{orderId}")
    public ResponseEntity<?> getCountOrderDetails(@PathVariable(name = "orderId") String orderId) {
        return ResponseEntity.ok().body(orderDetailRepository.findByOrder_OrderID(orderId).size());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllOrderDetailByUser(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<MyBookingResponse> orderDetailByUserPage = orderDetailService.getMyBooking(pageNo, pageSize);
        return new ResponseEntity<>(orderDetailByUserPage, HttpStatus.OK);
    }

    @PutMapping("/{id_order_detail}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_ADMIN')")
    public ResponseEntity<?> updateOrderDetail(@RequestBody OrderDetailRequest OrderDetailRequest,
                                               @PathVariable("id_order_detail") String orderDetailId) {

        OrderDetailResponse orderDetail = orderDetailService.updateOrderDetail(orderDetailId, OrderDetailRequest);
        return new ResponseEntity<>(orderDetail, HttpStatus.CREATED);
    }

}
