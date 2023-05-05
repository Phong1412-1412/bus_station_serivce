package com.busstation.controller.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.busstation.entities.Comment;
import com.busstation.payload.request.CommentRequest;
import com.busstation.payload.response.CommentResponse;
import com.busstation.services.CommentService;

@Controller
public class CommentControllerSocket {

	@Autowired
	private CommentService commentService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@GetMapping("/trips/{tripId}/comments")
	public String getCommentsByTripId(@PathVariable String tripId, Model model,
			@RequestParam(defaultValue = "1") int rating, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Comment> comments = commentService.getCommentsByTripId(tripId, rating, pageable);

		model.addAttribute("tripId", tripId);
		model.addAttribute("comments", comments);
		return "comment";
	}

	@MessageMapping("/comments/{tripId}")
	public ResponseEntity<CommentResponse> createComment(@DestinationVariable String tripId,
			@RequestBody CommentRequest request, Authentication authentication) {

		if (authentication == null) {
			throw new RuntimeException("Cannot get authentication information");
		}

		String username;
		if (authentication instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oauth2Authentication = (OAuth2AuthenticationToken) authentication;
			username = (String) oauth2Authentication.getPrincipal().getAttribute("email");
		} else {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			username = userDetails.getUsername();
		}
		CommentResponse response = commentService.createComment(tripId, username, request);

		simpMessagingTemplate.convertAndSend("/topic/comments/" + tripId, response);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
