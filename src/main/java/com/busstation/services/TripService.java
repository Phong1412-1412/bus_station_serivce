package com.busstation.services;


import com.busstation.entities.User;
import com.busstation.payload.request.SearchTripRequest;
import com.busstation.payload.request.TripRequest;
import com.busstation.payload.response.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TripService {
    TripResponse createTrip(TripRequest tripRequest);

    TripResponse updateTrip(String id, TripRequest newTripRequest);

    Boolean deleteTrip(String id);

    Page<SearchTripResponse> getAllTripsByProvinceStartAndProvinceEndDateTime(SearchTripRequest searchTripRequest, int pageNo, int pageSize);

    Page<TripResponse> getAllTrips(int pageNo, int pageSize);

    Page<UserByTripIdResponse> getAllUserByTrip(String tripId, int pageNo, int pageSize);

    void updateOnGoing(String tripId);

    void updateComplete(String tripId);

    List<UserOrderByTripIdResponse> getUsersByTripId(String tripId);

}
