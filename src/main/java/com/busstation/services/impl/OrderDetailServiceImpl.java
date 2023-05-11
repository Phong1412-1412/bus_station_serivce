package com.busstation.services.impl;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.busstation.entities.Account;
import com.busstation.entities.Chair;
import com.busstation.entities.Order;
import com.busstation.entities.OrderDetail;
import com.busstation.entities.Ticket;
import com.busstation.entities.Trip;
import com.busstation.entities.TripUser;
import com.busstation.entities.User;
import com.busstation.exception.DataNotFoundException;
import com.busstation.payload.request.OrderDetailRequest;
import com.busstation.payload.response.ChairResponse;
import com.busstation.payload.response.MyBookingResponse;
import com.busstation.payload.response.OrderDetailResponse;
import com.busstation.payload.response.OrderResponse;
import com.busstation.payload.response.TicketResponse;
import com.busstation.payload.response.UserResponse;
import com.busstation.repositories.AccountRepository;
import com.busstation.repositories.ChairRepository;
import com.busstation.repositories.OrderDetailRepository;
import com.busstation.repositories.OrderRepository;
import com.busstation.repositories.TicketRepository;
import com.busstation.repositories.TripRepository;
import com.busstation.repositories.TripUserRepository;
import com.busstation.repositories.UserRepository;
import com.busstation.services.OrderDetailService;
import com.busstation.utils.GetUserUtil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ChairRepository chairRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripUserRepository tripUserRepository;


    @Override
    public OrderDetailResponse updateOrderDetail(String orderDetailId, OrderDetailRequest orderDetailRequest) {

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(() -> new EntityNotFoundException("Order Detail does not exist"));

        Chair chair = chairRepository.findById(orderDetailRequest.getUpdateChairId()).orElseThrow(() -> new EntityNotFoundException("chair does not exist"));

        Order order = orderRepository.findById(orderDetail.getOrder().getOrderID()).orElseThrow(() -> new EntityNotFoundException("Order does not exist"));

        Optional<Ticket> ticket = ticketRepository.findByAddressStartAndAddressEnd(orderDetailRequest.getAddressStart(), orderDetailRequest.getAddressEnd());

        if (!ticket.isPresent()) {
            throw new DataNotFoundException("Ticket not found");
        }

        orderDetail.setStatus(orderDetailRequest.getStatus());
        orderDetail.setChair(chair);
        orderDetail.setOrder(order);
        orderDetail.setTicket(ticket.get());
        orderDetail.setUpdatedAt(new Date());

        OrderDetail updateOrderDetail = orderDetailRepository.save(orderDetail);

        if(updateOrderDetail.getStatus() == false){
            deleteUserToTrip(updateOrderDetail, orderDetailRequest.getTripId());
        }else {
            TripUser tripUser = tripUserRepository.findTripUserByUserId(orderDetail.getOrder().getUser().getUserId());
            if(Objects.isNull(tripUser)){
                addUserToTrip(orderDetailRequest.getTripId(),orderDetail.getOrder().getUser().getUserId());
            }
        }

        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetailResponse.setStatus(orderDetail.getStatus());
        orderDetailResponse.setChair(setupChairResponse(chair));
        orderDetailResponse.setOrder(setupOrderResponse(order));
        orderDetailResponse.setTicket(setupTicketResponse(ticket.get()));


        return orderDetailResponse;
    }

    @Override
    public Page<OrderDetailResponse> getAllOrderDetail(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createAt").descending());

        Page<OrderDetail> orderDetails = orderDetailRepository.findAll(pageable);

        Page<OrderDetailResponse> orderDetailPage = orderDetails.map(OrderDetailResponse::new);

        return orderDetailPage;
    }

    public UserResponse setupUserResponse(Order order) {
        User user = userRepository.findById(order.getUser().getUserId()).orElseThrow(() -> new EntityNotFoundException("User does not exist"));

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setStatus(user.getStatus());
        userResponse.setFullName(user.getFullName());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setEmail(user.getEmail());
        userResponse.setAddress(user.getAddress());

        return userResponse;
    }

    public ChairResponse setupChairResponse(Chair chair) {

        ChairResponse chairResponse = new ChairResponse();
        chairResponse.setChairId(chair.getChairId());
        chairResponse.setChairNumber(chair.getChairNumber());
        chairResponse.setCarId(chair.getCar().getCarId());

        return chairResponse;
    }

    public OrderResponse setupOrderResponse(Order order) {

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderID());
        orderResponse.setUser(setupUserResponse(order));

        return orderResponse;
    }

    public TicketResponse setupTicketResponse(Ticket ticket) {

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setTicketId(ticket.getTicketId());
        ticketResponse.setAddressStart(ticket.getAddressStart());
        ticketResponse.setAddressEnd(ticket.getAddressEnd());
        ticketResponse.setPrice(ticket.getPrice());
        ticket.setPickupLocation(ticket.getPickupLocation());
        ticket.setDropOffLocation(ticket.getDropOffLocation());

        return ticketResponse;
    }

    public void addUserToTrip(String tripId, String userId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new EntityNotFoundException("Trip not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        trip.getUsers().add(user);
        tripRepository.save(trip);
    }

    public void deleteUserToTrip(OrderDetail orderDetail, String tripId) {

        User user = userRepository.findUserByOrders(orderDetail.getOrder());

        if(Objects.nonNull(user)){
            Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new EntityNotFoundException("Trip not found"));

            trip.getUsers().remove(user);
            tripRepository.save(trip);
        }
    }

    @Override
    public Page<MyBookingResponse> getMyBooking(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createAt").descending());

        Account account = accountRepository.findByusername(new GetUserUtil().GetUserName());

        Page<Order> orderPage = orderRepository.findAllByUserId(account.getUser().getUserId(), pageable);

        return orderPage.map(order -> new MyBookingResponse(order, orderRepository));
    }

}
