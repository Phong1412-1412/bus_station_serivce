package com.busstation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import com.busstation.entities.Comment;
import com.busstation.payload.request.CommentRequest;
import com.busstation.payload.response.CommentResponse;
import com.busstation.services.CommentService;
import com.busstation.utils.SecurityUtils;

@RestController
@EnableWebSocketMessageBroker 
@RequestMapping("/api/v1")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@GetMapping("/trips/{tripId}/comments")
	public ResponseEntity<Page<CommentResponse>> getCommentsByTripId(@PathVariable String tripId,
																	 @RequestParam(defaultValue = "3") int rating, @RequestParam(defaultValue = "0") int page,
																	 @RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Comment> comments = commentService.getCommentsByTripId(tripId, rating, pageable);

		return new ResponseEntity<>(comments.map(CommentResponse::new), HttpStatus.OK);
	}

	@PostMapping("/trips/{tripId}/comments")
	public ResponseEntity<CommentResponse> createComment(@PathVariable String tripId,
														 @RequestBody CommentRequest request) {

		CommentResponse response = commentService.createComment(tripId, SecurityUtils.getUserName(), request);
		
		simpMessagingTemplate.convertAndSend("/topic/comments/"+ tripId, response);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}