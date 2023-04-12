package com.busstation.payload.response;

import com.busstation.entities.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

    private String orderDetailId;

    private String status;

    private OrderResponse order;

    private ChairResponse chair;

    private TicketResponse ticket;

    public OrderDetailResponse(OrderDetail orderDetail){

        this.orderDetailId = orderDetail.getOrderDetailId();
        this.status = orderDetail.getStatus();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderID(orderDetail.getOrder().getOrderID());

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(orderDetail.getOrder().getUser().getUserId());
        userResponse.setFullName(orderDetail.getOrder().getUser().getFullName());
        userResponse.setPhoneNumber(orderDetail.getOrder().getUser().getPhoneNumber());
        userResponse.setEmail(orderDetail.getOrder().getUser().getEmail());
        userResponse.setAddress(orderDetail.getOrder().getUser().getAddress());
        userResponse.setStatus(orderDetail.getOrder().getUser().getStatus());

        orderResponse.setUser(userResponse);

        this.order = orderResponse;

        ChairResponse chairResponse = new ChairResponse();
        chairResponse.setChairId(orderDetail.getChair().getChairId());
        chairResponse.setCarId(orderDetail.getChair().getCar().getCarId());
        chairResponse.setChairNumber(orderDetail.getChair().getChairNumber());

        this.chair = chairResponse;

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setTicketId(orderDetail.getTicket().getTicketId());
        ticketResponse.setAddressStart(orderDetail.getTicket().getAddressStart());
        ticketResponse.setAddressEnd(orderDetail.getTicket().getAddressEnd());
        ticketResponse.setPrice(orderDetail.getTicket().getPrice());

        this.ticket = ticketResponse;
    }
}