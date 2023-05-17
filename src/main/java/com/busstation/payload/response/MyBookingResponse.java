package com.busstation.payload.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.busstation.entities.Order;
import com.busstation.entities.OrderDetail;
import com.busstation.enums.TripStatus;
import com.busstation.repositories.OrderRepository;

import lombok.Data;

@Data
public class MyBookingResponse {

    private String orderId;
    
    private String paymentMethodName;

    private TripStatus tripStatus;

    private UserResponse user;

    private TripResponse trip;

    private BigDecimal sumOrder;

    private List<OrderDetailsResponse> details;

    public MyBookingResponse(Order order, OrderRepository orderRepository) {
        super();

        
        this.orderId = order.getOrderID();
        if(order.getPaymentMethod()!= null) {
            this.paymentMethodName = order.getPaymentMethod().getPaymentMethod();      	
        }
        this.sumOrder = orderRepository.getSumOrder(order.getOrderID());

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(order.getUser().getUserId());
        userResponse.setFullName(order.getUser().getFullName());
        userResponse.setPhoneNumber(order.getUser().getPhoneNumber());
        userResponse.setEmail(order.getUser().getEmail());
        userResponse.setAddress(order.getUser().getAddress());
        userResponse.setStatus(order.getUser().getStatus());
        this.user = userResponse;
        
        TripResponse tripResponse = new TripResponse();
        tripResponse.setTripId(order.getTrip().getTripId());
        tripResponse.setProvinceStart(order.getTrip().getProvinceStart());
        tripResponse.setProvinceEnd(order.getTrip().getProvinceEnd());;
        tripResponse.setTimeStart(order.getTrip().getTimeStart());
        tripResponse.setStatus(order.getTrip().getStatus());
        this.tripStatus = tripResponse.getStatus();
        this.trip = tripResponse;

        List<OrderDetailsResponse> lstOderDetails = new ArrayList<>();

        for(OrderDetail i: order.getOrderDetails()) {
            OrderDetailsResponse od = new OrderDetailsResponse(i);
            lstOderDetails.add(od);
        }

        this.details = lstOderDetails;
    }

}

@Data
class OrderDetailsResponse{

    private String orderDetailId;

    private Boolean status;

    private ChairResponse chair;

    private TicketResponse ticket;

    public OrderDetailsResponse(OrderDetail orderDetail){

        this.orderDetailId = orderDetail.getOrderDetailId();
        
        this.status = orderDetail.getStatus();

        ChairResponse chairResponse = new ChairResponse();
        chairResponse.setChairId(orderDetail.getChair().getChairId());
        chairResponse.setCarId(orderDetail.getChair().getCar().getCarId());
        chairResponse.setChairNumber(orderDetail.getChair().getChairNumber());
        chairResponse.setStatus(orderDetail.getChair().getStatus());

        this.chair = chairResponse;

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setTicketId(orderDetail.getTicket().getTicketId());
        ticketResponse.setPrice(orderDetail.getTicket().getPrice());

        this.ticket = ticketResponse;
    }

}
