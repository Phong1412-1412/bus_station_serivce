package com.busstation.services;


import com.busstation.entities.Trip;
import com.busstation.payload.request.SearchTripRequest;
import com.busstation.payload.request.TripRequest;
import com.busstation.payload.response.SearchTripResponse;
import com.busstation.payload.response.TripResponse;
import com.busstation.payload.response.UserByTripIdResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface TripService {
    TripResponse createTrip(TripRequest tripRequest);

    TripResponse updateTrip(String id, TripRequest newTripRequest);

    Boolean deleteTrip(String id);

    Page<SearchTripResponse> getAllTripsByProvinceStartAndProvinceEndDateTime(SearchTripRequest searchTripRequest, int pageNo, int pageSize);

    Page<TripResponse> getAllTrips(int pageNo, int pageSize);

    Page<UserByTripIdResponse> getAllUserByTrip(String tripId, int pageNo, int pageSize);

}
