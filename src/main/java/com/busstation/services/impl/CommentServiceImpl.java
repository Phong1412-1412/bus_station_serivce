package com.busstation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.busstation.entities.Account;
import com.busstation.entities.Comment;
import com.busstation.entities.Trip;
import com.busstation.entities.User;
import com.busstation.exception.DataNotFoundException;
import com.busstation.payload.request.CommentRequest;
import com.busstation.payload.response.CommentResponse;
import com.busstation.repositories.AccountRepository;
import com.busstation.repositories.CommentRepository;
import com.busstation.repositories.TripRepository;
import com.busstation.repositories.UserRepository;
import com.busstation.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Page<Comment> getCommentsByTripId(String tripId, int rating, Pageable pageable) {
		return commentRepository.findByTripIdAndRatingGreaterThanEqual(tripId, rating, pageable);
	}

	@Override
	public CommentResponse createComment(String tripId, String username, CommentRequest request) {

		Trip trip = tripRepository.findById(tripId)
				.orElseThrow(() -> new DataNotFoundException("Trip not found with id " + tripId));

		Account account = accountRepository.findByusername(username);
		if (account == null) {
			throw new DataNotFoundException("User not found");
		}

		User user = userRepository.findByAccountId(account.getAccountId());

		Comment comment = new Comment();
		comment.setContent(request.getContent());
		comment.setRating(request.getRating());
		comment.setTrip(trip);
		comment.setUser(user);
		commentRepository.save(comment);
		CommentResponse response = new CommentResponse(comment);
		return response;
	}

}
