package com.busstation.services.impl;

import com.busstation.entities.*;
import com.busstation.exception.DataNotFoundException;
import com.busstation.payload.request.OrderDetailRequest;
import com.busstation.payload.response.*;
import com.busstation.repositories.*;
import com.busstation.services.OrderService;
import com.busstation.utils.GetUserUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ChairRepository chairRepository;


    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public OrderResponse createOrder(OrderDetailRequest orderDetailRequest) {

        Account account = accountRepository.findByusername(new GetUserUtil().GetUserName());

        User user = userRepository.findById(account.getUser().getUserId()).orElseThrow(()->new RuntimeException("User does not exist"));

        Chair chair = chairRepository.findById(orderDetailRequest.getChairId()).orElseThrow(()->new EntityNotFoundException("chair does not exist"));

        Optional<Ticket> ticket = ticketRepository.findByAddressStartAndAddressEnd(orderDetailRequest.getAddressStart(), orderDetailRequest.getAddressEnd());

        if(!ticket.isPresent()){
            throw new DataNotFoundException("Ticket not found");
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setStatus(user.getStatus());
        userResponse.setFullName(user.getFullName());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setEmail(user.getEmail());
        userResponse.setAddress(user.getAddress());

        Order order1 = new Order();
        order1.setUser(user);
        order1.setOrderID(getOrderId(chair.getCar().getTrips().getTripId()));


        Order newOrder = orderRepository.save(order1);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderID(newOrder.getOrderID());
        orderResponse.setUser(userResponse);



        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setStatus("Not Authenticated");
        orderDetail.setChair(chair);
        orderDetail.setOrder(newOrder);
        orderDetail.setTicket(ticket.get());

        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);

        addUserToTrip(chair.getCar().getTrips().getTripId(),user.getUserId());


        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setOrderDetailId(newOrderDetail.getOrderDetailId());
        orderDetailResponse.setStatus(newOrderDetail.getStatus());
        orderDetailResponse.setChair(setupChairResponse(chair));
        orderDetailResponse.setOrder(setupOrderResponse(newOrder));
        orderDetailResponse.setTicket(setupTicketResponse(ticket.get()));

        orderResponse.setOrderDetail(orderDetailResponse);


        return orderResponse;
    }

    @Override
    public OrderDetailResponse searchOrderById(String orderId) {

        OrderDetail orderDetail = orderDetailRepository.findByOrder_OrderID(orderId);

        Order order = orderRepository.findById(orderDetail.getOrder().getOrderID()).orElseThrow(() -> new EntityNotFoundException("Order does not exist"));

        Chair chair = chairRepository.findById(orderDetail.getChair().getChairId()).orElseThrow(()->new EntityNotFoundException("chair does not exist"));

        Ticket ticket = ticketRepository.findById(orderDetail.getTicket().getTicketId()).orElseThrow(()->new EntityNotFoundException("Ticker does not exist"));


        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();

        orderDetailResponse.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetailResponse.setStatus(orderDetail.getStatus());
        orderDetailResponse.setOrder(setupOrderResponse(order));
        orderDetailResponse.setTicket(setupTicketResponse(ticket));
        orderDetailResponse.setChair(setupChairResponse(chair));


        return orderDetailResponse;
    }

    public String getOrderId(String tripId){

        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int LENGTH = 15;

        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new EntityNotFoundException("Trip not found"));

        String provinceEnd = provinceEnd = Arrays.stream(trip.getProvinceEnd().split(" "))
                .map(s->s.charAt(0))
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append).toString();
        Province province = provinceRepository.findByName(trip.getProvinceEnd());
        provinceEnd += province.getProvinceId() + "-";

        boolean doWhile = true;
        String initial;

        do{
            initial = provinceEnd;
            Random random = new Random();
            StringBuilder builder = new StringBuilder();

            int length = LENGTH - provinceEnd.length();

            for(int i=0; i<length; i++){

                int index = random.nextInt(CHARACTERS.length());
                builder.append(CHARACTERS.charAt(index));
            }
            initial += builder;

            if(!orderRepository.findById(initial).isPresent()){
                doWhile = false;
            }

        }while (doWhile);

        return initial;
    }

    public void addUserToTrip(String tripId, String userId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new EntityNotFoundException("Trip not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        trip.getUsers().add(user);
        tripRepository.save(trip);
    }

    public UserResponse setupUserResponse(Order order){
        User user = userRepository.findById(order.getUser().getUserId()).orElseThrow(()->new EntityNotFoundException("User does not exist"));

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setStatus(user.getStatus());
        userResponse.setFullName(user.getFullName());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setEmail(user.getEmail());
        userResponse.setAddress(user.getAddress());

        return userResponse;
    }

    public ChairResponse setupChairResponse(Chair chair){

        ChairResponse chairResponse = new ChairResponse();
        chairResponse.setChairId(chair.getChairId());
        chairResponse.setChairNumber(chair.getChairNumber());
        chairResponse.setCarId(chair.getCar().getCarId());

        return chairResponse;
    }

    public OrderResponse  setupOrderResponse(Order order){

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderID(order.getOrderID());
        orderResponse.setUser(setupUserResponse(order));

        return orderResponse;
    }

    public TicketResponse setupTicketResponse(Ticket ticket){

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setTicketId(ticket.getTicketId());
        ticketResponse.setAddressStart(ticket.getAddressStart());
        ticketResponse.setAddressEnd(ticket.getAddressEnd());
        ticketResponse.setPrice(ticket.getPrice());

        return ticketResponse;
    }
}