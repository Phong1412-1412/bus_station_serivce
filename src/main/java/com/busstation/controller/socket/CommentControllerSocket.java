package com.busstation.controller.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.busstation.entities.Comment;
import com.busstation.services.CommentService;

@Controller
public class CommentControllerSocket {

	@Autowired
	private CommentService commentService;

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

}
