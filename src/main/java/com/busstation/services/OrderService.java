package com.busstation.services;

import org.springframework.data.domain.Page;

import com.busstation.payload.request.OrderRequest;
import com.busstation.payload.request.VerifyOrderInformationRequest;
import com.busstation.payload.response.MyBookingResponse;
import com.busstation.payload.response.OrderDetailResponse;
import com.busstation.payload.response.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest,String token);

    Boolean submitOrder(String orderId, String tripId);

    Page<OrderDetailResponse> searchOrderById(String orderId, int pageNo, int pageSize);

    Boolean deleteOrder(String orderId);
    
    MyBookingResponse getInformationNewOrder(String orderId);
   
}
