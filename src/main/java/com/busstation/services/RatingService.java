package com.busstation.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busstation.entities.Rating;
import com.busstation.payload.request.RatingRequest;
import com.busstation.payload.response.RatingResponse;

public interface RatingService {
	
	public Page<Rating> getAllRating(Pageable pageable);
	
	public RatingResponse getRatingByUser();

	public RatingResponse createRating(RatingRequest request);
	
	public RatingResponse updateRating(RatingRequest request);
	
	public void deleteRating();

}
