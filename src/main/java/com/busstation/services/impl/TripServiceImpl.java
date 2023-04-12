package com.busstation.services.impl;

import com.busstation.entities.Car;
import com.busstation.entities.Ticket;
import com.busstation.entities.Trip;
import com.busstation.entities.User;
import com.busstation.payload.request.SearchTripRequest;
import com.busstation.payload.request.TicketRequest;
import com.busstation.payload.request.TripRequest;
import com.busstation.payload.response.SearchTripResponse;
import com.busstation.payload.response.TicketResponse;
import com.busstation.payload.response.TripResponse;
import com.busstation.payload.response.UserByTripIdResponse;
import com.busstation.repositories.CarRepository;
import com.busstation.repositories.TicketRepository;
import com.busstation.repositories.TripRepository;
import com.busstation.repositories.UserRepository;
import com.busstation.services.TicketService;
import com.busstation.services.TripService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    @Transactional
    public TripResponse createTrip(TripRequest tripRequest) {

        Optional<Trip> checkTrip = tripRepository
                .findByProvinceStartAndProvinceEnd(tripRequest.getProvinceStart(), tripRequest.getProvinceEnd(), tripRequest.getTimeStart());

        Optional<Ticket> checkTicket = ticketRepository
                .findByAddressStartAndAddressEnd(tripRequest.getProvinceStart(),tripRequest.getProvinceEnd());

        if(checkTrip.isPresent()){
            return null;
        }

        Trip trip = new Trip();
        trip.setProvinceStart(tripRequest.getProvinceStart());
        trip.setProvinceEnd(tripRequest.getProvinceEnd());
        trip.setTimeStart(tripRequest.getTimeStart());
        trip.setStatus(true);

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

        trip.setStatus(false);
        tripRepository.save(trip);

        List<Car> cars = carRepository.findByTrips_TripId(id);

        for (Car car : cars) {
            car.setStatus(false);
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
                    return new SearchTripResponse(trip, price);
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
                    return new SearchTripResponse(trip, price);
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
                return new SearchTripResponse(trip, price);
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
}
