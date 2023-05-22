package com.busstation.services.impl;

import java.time.LocalDateTime;

import com.busstation.exception.DataNotFoundException;
import com.busstation.exception.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.busstation.entities.Account;
import com.busstation.entities.Rating;
import com.busstation.payload.request.RatingRequest;
import com.busstation.payload.response.RatingResponse;
import com.busstation.repositories.AccountRepository;
import com.busstation.repositories.RatingRepository;
import com.busstation.services.RatingService;
import com.busstation.utils.SecurityUtils;

@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public Page<Rating> getAllRating(Pageable pageable) {		
		return ratingRepository.findAll(pageable);
	}

	@Override
	public RatingResponse getRatingByUser() {
		
		Rating rating = findRatingbyUser();
		if(rating == null) {
			return null;
		}
		RatingResponse response = new RatingResponse(rating);
		return response;
	}

	@Override
	public RatingResponse createRating(RatingRequest request) {
		
		String username = SecurityUtils.getUserName();
		Account account = accountRepository.findByusername(username);
		if(account.getUser().getOrders().size() == 0) {
			throw new DataNotFoundException("Cannot rate. Please order our service first.");
		}
		Rating rating = new Rating();
		rating.setUser(account.getUser());
		rating.setContent(request.getContent());
		rating.setRating(request.getRating());
		ratingRepository.save(rating);

		return new RatingResponse(rating);
	}

	@Override
	public RatingResponse updateRating(RatingRequest request) {
		
		Rating rating = findRatingbyUser();
		rating.setContent(request.getContent());
		rating.setRating(request.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		ratingRepository.save(rating);
		
		RatingResponse response = new RatingResponse(rating);
		return response;
	}

	@Override
	public void deleteRating() {
		Rating rating = findRatingbyUser();
		ratingRepository.delete(rating);
	}
	
	private Rating findRatingbyUser() {
		String username = SecurityUtils.getUserName();
		Account account = accountRepository.findByusername(username);
		Rating rating = ratingRepository.findRatingByUserId(account.getUser().getUserId());
		return rating;
		
	}

}
