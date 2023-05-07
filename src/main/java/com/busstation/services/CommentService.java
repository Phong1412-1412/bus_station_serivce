package com.busstation.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busstation.entities.Comment;
import com.busstation.payload.request.CommentRequest;
import com.busstation.payload.response.CommentResponse;

import java.util.List;

public interface CommentService {

	public Page<Comment> getCommentsByTripId(String tripId, Pageable pageable);

	public CommentResponse createComment(String tripid, String username, CommentRequest Request);

	public List<CommentResponse> getAllComments(String tripId);

}
