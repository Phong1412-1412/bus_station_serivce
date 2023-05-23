package com.busstation.services.impl;

import com.busstation.entities.*;
import com.busstation.enums.TripStatus;
import com.busstation.exception.DataNotFoundException;
import com.busstation.payload.request.SearchTripRequest;
import com.busstation.payload.request.TicketRequest;
import com.busstation.payload.request.TripRequest;
import com.busstation.payload.response.*;
import com.busstation.repositories.*;
import com.busstation.services.ChairService;
import com.busstation.services.TicketService;
import com.busstation.services.TripService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private ChairService chairService;

    @Autowired
    private TypeCarRepository typeCarRepositoryl;

    @Override
    @Transactional
    public TripResponse createTrip(TripRequest tripRequest) {

        Optional<Trip> checkTrip = tripRepository
                .findByProvinceStartAndProvinceEnd(tripRequest.getProvinceStart(), tripRequest.getProvinceEnd(), tripRequest.getTimeStart());

        Optional<Ticket> checkTicket = ticketRepository.findByAddressStartAndAddressEnd(tripRequest.getProvinceStart(),tripRequest.getProvinceEnd());

        if(checkTrip.isPresent()){
            return null;
        }

        Trip trip = new Trip();
        trip.setProvinceStart(tripRequest.getProvinceStart());
        trip.setProvinceEnd(tripRequest.getProvinceEnd());
        trip.setTimeStart(tripRequest.getTimeStart());
        trip.setStatus(TripStatus.PREPARE);

        Trip newTrip = tripRepository.save(trip);

        TicketRequest ticketRequest = new TicketRequest(tripRequest.getProvinceStart(),
                tripRequest.getProvinceEnd(),
                tripRequest.getPrice());

        TicketResponse ticketResponse;

        if(checkTicket.isPresent()){
            ticketResponse = ticketService.updateTicket(checkTicket.get().getTicketId(), ticketRequest);
        }
        else {
            ticketResponse = ticketService.addTicket(ticketRequest);
        }

        TripResponse tripResponse = new TripResponse();
        tripResponse.setTripId(newTrip.getTripId());
        tripResponse.setProvinceStart(newTrip.getProvinceStart());
        tripResponse.setProvinceEnd(newTrip.getProvinceEnd());
        tripResponse.setTimeStart(newTrip.getTimeStart());
        tripResponse.setPrice(ticketResponse.getPrice());
        tripResponse.setStatus(newTrip.getStatus());
        return tripResponse;
    }

    @Override
    @Transactional
    public TripResponse updateTrip(String id, TripRequest newTripRequest) {

        Optional<Trip> checkTrip = tripRepository
                .findByProvinceStartAndProvinceEnd(newTripRequest.getProvinceStart(), newTripRequest.getProvinceEnd(), newTripRequest.getTimeStart());

        Optional<Ticket> checkTicket = ticketRepository
                .findByAddressStartAndAddressEnd(newTripRequest.getProvinceStart(),newTripRequest.getProvinceEnd());

        if(checkTrip.isPresent()){
            if(!checkTrip.get().getTripId().equals(id)){
                return null;
            }
        }

        Trip trip = tripRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip does not exist"));

        trip.setProvinceStart(newTripRequest.getProvinceStart());
        trip.setProvinceEnd(newTripRequest.getProvinceEnd());
        trip.setTimeStart(newTripRequest.getTimeStart());
        trip.setUpdateAt(new Date());

        tripRepository.save(trip);

        TicketRequest ticketRequest = new TicketRequest(newTripRequest.getProvinceStart(),
                newTripRequest.getProvinceEnd(),
                newTripRequest.getPrice());

        TicketResponse ticketResponse;

        if(checkTicket.isPresent()){
            ticketResponse = ticketService.updateTicket(checkTicket.get().getTicketId(), ticketRequest);
        }
        else {
            ticketResponse = ticketService.addTicket(ticketRequest);
        }

        TripResponse tripResponse = new TripResponse();
        tripResponse.setTripId(trip.getTripId());
        tripResponse.setProvinceStart(trip.getProvinceStart());
        tripResponse.setProvinceEnd(trip.getProvinceEnd());
        tripResponse.setTimeStart(trip.getTimeStart());
        tripResponse.setPrice(ticketResponse.getPrice());


        return tripResponse;
    }

    @Override
    public Boolean deleteTrip(String id) {

        Trip trip = tripRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip does not exist"));

        trip.setStatus(TripStatus.PREPARE);
        tripRepository.save(trip);

        List<Car> cars = carRepository.findByTrips_TripId(id);

        for (Car car : cars) {

            if(Objects.isNull(car.getTrips())){

                car.setStatus(false);
            }
            carRepository.save(car);
        }

        deleteUserToTrip(id);

        return true;
    }

    @Override
    public Page<SearchTripResponse> getAllTripsByProvinceStartAndProvinceEndDateTime(SearchTripRequest searchTripRequest,
                                                                                     int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createAt").ascending());

        if (searchTripRequest.getProvinceStart() == null && searchTripRequest.getProvinceEnd() == null) {

            Page<Trip> trips = tripRepository.findAllTrips(pageable);

            return trips.map(trip -> {
                Optional<Ticket> ticket = ticketRepository.findByAddressStartAndAddressEnd(trip.getProvinceStart(), trip.getProvinceEnd());
                if (ticket.isPresent()) {
                    Ticket getTicket = ticket.get();
                    BigDecimal price = getTicket.getPrice();
                    List<Order> orders = orderRepository.findAllByTrip_TripId(trip.getTripId());
                    List<String> chairId = new ArrayList<>();
                    for(Order itemOrder : orders){
                        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderID(itemOrder.getOrderID());
                        for(OrderDetail orderDetail : orderDetails){
                            chairId.add(orderDetail.getChair().getChairId());
                        }
                    }
                    return new SearchTripResponse(trip, price, chairId, chairService, typeCarRepositoryl);
                }
                return null;
            });
        }

        if (searchTripRequest.getDateTime() == null) {

            Page<Trip> trips = tripRepository.findByProvinceStartAndProvinceEnd(searchTripRequest.getProvinceStart(),
                    searchTripRequest.getProvinceEnd(), pageable);

            return trips.map(trip -> {
                Optional<Ticket> ticket = ticketRepository.findByAddressStartAndAddressEnd(trip.getProvinceStart(), trip.getProvinceEnd());
                if (ticket.isPresent()) {
                    Ticket getTicket = ticket.get();
                    BigDecimal price = getTicket.getPrice();
                    List<Order> orders = orderRepository.findAllByTrip_TripId(trip.getTripId());
                    List<String> chairId = new ArrayList<>();
                    for(Order itemOrder : orders){
                        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderID(itemOrder.getOrderID());
                        for(OrderDetail orderDetail : orderDetails){
                            chairId.add(orderDetail.getChair().getChairId());
                        }
                    }
                    return new SearchTripResponse(trip, price, chairId, chairService, typeCarRepositoryl);
                }
                return null;
            });
        }

        Page<Trip> trips = tripRepository.findByProvinceStartAndProvinceEndAndDateTime(searchTripRequest.getProvinceStart(),
                searchTripRequest.getProvinceEnd(),
                searchTripRequest.getDateTime(), pageable);

        return trips.map(trip -> {
            Optional<Ticket> ticket = ticketRepository.findByAddressStartAndAddressEnd(trip.getProvinceStart(), trip.getProvinceEnd());
            if (ticket.isPresent()) {
                Ticket getTicket = ticket.get();
                BigDecimal price = getTicket.getPrice();
                List<Order> orders = orderRepository.findAllByTrip_TripId(trip.getTripId());
                List<String> chairId = new ArrayList<>();
                for(Order itemOrder : orders){
                    List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderID(itemOrder.getOrderID());
                    for(OrderDetail orderDetail : orderDetails){
                        chairId.add(orderDetail.getChair().getChairId());
                    }
                }
                return new SearchTripResponse(trip, price, chairId, chairService, typeCarRepositoryl);
            }
            return null;
        });
    }

    @Override
    public Page<TripResponse> getAllTrips(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createAt").ascending());

        Page<Trip> trips = tripRepository.findAll(pageable);

        return trips.map(trip -> {
            Optional<Ticket> ticket = ticketRepository.findByAddressStartAndAddressEnd(trip.getProvinceStart(), trip.getProvinceEnd());
            if (ticket.isPresent()) {
                Ticket getTicket = ticket.get();
                BigDecimal price = getTicket.getPrice();
                return new TripResponse(trip, price);
            }
            return null;
        });
    }

    @Override
    public Page<UserByTripIdResponse> getAllUserByTrip(String tripId, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<User> users = userRepository.findByTrips_TripId(tripId, pageable);

        Optional<Trip> checkTrip = tripRepository.findById(tripId);

        if (checkTrip.isPresent()) {

            Trip getTrip = checkTrip.get();

            Optional<Ticket> ticket = ticketRepository.findByAddressStartAndAddressEnd(getTrip.getProvinceStart(), getTrip.getProvinceEnd());
            BigDecimal price;

            if (ticket.isPresent()) {
                Ticket getTicket = ticket.get();
                price = getTicket.getPrice();
            } else {
                price = null;
            }
            return users.map(user -> new UserByTripIdResponse(user, getTrip, price));
        }
        return null;
    }

    @Override
    public void updateOnGoing(String tripId) {
        Trip trip = tripRepository.findByTripId(tripId).orElseThrow(() -> new DataNotFoundException("Trip Not exists!"));
        if(trip.getStatus() != TripStatus.COMPLETE && trip.getStatus() == TripStatus.PREPARE) {
            trip.setStatus(TripStatus.ONGOING);
            tripRepository.save(trip);
        }
    }

    @Override
    public void updateComplete(String tripId) {
        Trip trip = tripRepository.findByTripId(tripId).orElseThrow(() -> new DataNotFoundException("Trip Not exists!"));
        if(trip.getStatus() != TripStatus.PREPARE && trip.getStatus() == TripStatus.ONGOING) {
            trip.setStatus(TripStatus.COMPLETE);
            tripRepository.save(trip);
        }
    }

    public void deleteUserToTrip(String tripId) {
        List<User> users = userRepository.findAllByTrips_TripId(tripId);
        if (!users.isEmpty()) {

            Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new EntityNotFoundException("Trip not found"));

            for (User itemUser : users) {

                User user = userRepository.findById(itemUser.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

                trip.getUsers().remove(user);
            }
            tripRepository.save(trip);
        }
    }

    @Override
    public List<UserOrderByTripIdResponse> getUsersByTripId(String tripId) {
         User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
         List<Order> orders = orderRepository.findOrderByTripIdAndEmployeeId(tripId, user.getUserId());
        if(orders.isEmpty()) {
            throw new DataNotFoundException("Not order for trip::");
        }

        List<UserOrderByTripIdResponse> userOrderByTripIdResponses = new ArrayList<>();
        for(Order order: orders) {
            UserOrderByTripIdResponse userOrderByTripIdResponse = new UserOrderByTripIdResponse();
            UserResponse userResponse = new UserResponse();
            BigDecimal total = BigDecimal.ZERO;
            userResponse.setUserId(order.getUser().getUserId());
            userResponse.setFullName(order.getUser().getFullName());
            userResponse.setEmail(order.getUser().getEmail());
            userResponse.setPhoneNumber(order.getUser().getPhoneNumber());
            userResponse.setAddress(order.getUser().getAddress());
            userResponse.setStatus(order.getUser().getStatus());
            String chairs = "";
            for(OrderDetail orderDetail: order.getOrderDetails()) {
                BigDecimal priceTicket = orderDetail.getTicket().getPrice();
                total = total.add(priceTicket);
                chairs = chairs.concat("/").concat(String.valueOf(orderDetail.getChair().getChairNumber()));
            }
            if (chairs.startsWith("/")) {
                chairs = chairs.substring(1); // Xóa ký tự '/' ở đầu chuỗi
            }
            userOrderByTripIdResponse.setUserResponse(userResponse);
            userOrderByTripIdResponse.setPaymentMethod(order.getPaymentMethod());
            userOrderByTripIdResponse.setToTalPrice(total);
            userOrderByTripIdResponse.setChairNumber(chairs);
            userOrderByTripIdResponse.setOrderId(order.getOrderID());
            userOrderByTripIdResponse.setTripStatus(order.getTrip().getStatus());
            userOrderByTripIdResponses.add(userOrderByTripIdResponse);
        }
        return userOrderByTripIdResponses;
    }
}
