package com.busstation.services;

import org.springframework.data.domain.Page;

import com.busstation.payload.request.OrderRequest;
import com.busstation.payload.response.MyBookingResponse;
import com.busstation.payload.response.OrderDetailResponse;
import com.busstation.payload.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest,String token);

    Boolean submitOrder(String orderId, String tripId, Long paymentId);

    Page<OrderDetailResponse> searchOrderById(String orderId, int pageNo, int pageSize);

    Boolean deleteOrder(String orderId);
    
    MyBookingResponse getInformationNewOrder(String orderId);

    Integer getOrderByTripAndUser(String userId);

}
