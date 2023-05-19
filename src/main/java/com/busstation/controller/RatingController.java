package com.busstation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busstation.entities.Rating;
import com.busstation.payload.request.RatingRequest;
import com.busstation.payload.response.RatingResponse;
import com.busstation.services.RatingService;

@RestController
@RequestMapping("/api/v1/ratings")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	@GetMapping("")
	public ResponseEntity<Page<RatingResponse>> getAllRating(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Rating> ratings = ratingService.getAllRating(pageable);
		
		return new ResponseEntity<>(ratings.map(RatingResponse::new), HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createRating(@RequestBody RatingRequest request) {

		try {
			RatingResponse response = ratingService.createRating(request);
			return new ResponseEntity<>(response, HttpStatus.CREATED);			
		}catch (Exception e) {
			return new ResponseEntity<>("Failed! "+e.getMessage(), HttpStatus.BAD_REQUEST);	
		}

	}
	@PutMapping("")
	public ResponseEntity<?> updateRating(@RequestBody RatingRequest request) {

		try {
			RatingResponse response = ratingService.updateRating(request);
			return new ResponseEntity<>(response, HttpStatus.OK);			
		}catch (Exception e) {
			return new ResponseEntity<>("Failed! "+e.getMessage(), HttpStatus.BAD_REQUEST);	
		}

	}
	@DeleteMapping("")
	public ResponseEntity<?> deleteRating() {

		try {
			ratingService.deleteRating();
			return new ResponseEntity<>("Delete rating success!", HttpStatus.OK);			
		}catch (Exception e) {
			return new ResponseEntity<>("Delete rating Failed! "+e.getMessage(), HttpStatus.BAD_REQUEST);	
		}

	}
	
	@GetMapping("/user")
	public ResponseEntity<?> getRatingbyUser() {

		RatingResponse response = ratingService.getRatingByUser();
		if(response == null) {
			return new ResponseEntity<>("You haven't rated us service", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
