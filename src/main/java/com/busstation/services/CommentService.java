package com.busstation.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busstation.entities.Comment;
import com.busstation.payload.request.CommentRequest;
import com.busstation.payload.response.CommentResponse;

public interface CommentService {

	public Page<Comment> getCommentsByTripId(String tripId, int rating, Pageable pageable);

	public CommentResponse createComment(String tripid, String username, CommentRequest Request);

}
