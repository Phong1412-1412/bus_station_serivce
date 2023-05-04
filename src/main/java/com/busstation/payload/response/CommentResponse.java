package com.busstation.payload.response;

import java.time.LocalDateTime;

import com.busstation.entities.Comment;
import com.busstation.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CommentResponse {

    private String commentId;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private int rating;
    //private CommentTripResponse trip;
    private CommentUserResponse user;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.rating = comment.getRating();
        //this.trip = new CommentTripResponse(comment.getTrip());
        this.user = new CommentUserResponse(comment.getUser());
    }
}

//@Data
//class CommentTripResponse{
//	
//	private String tripId;
//	private String getAddressStart;
//    private String getAddressEnd; 
//    
//    public CommentTripResponse(Trip trip) {
//    	this.tripId = trip.getTicketId();
//    	this.getAddressStart = trip.getAddressStart();
//    	this.getAddressEnd = trip.getAddressEnd();
//    }
//}

@Data 
class CommentUserResponse{
	
	private String userId;
	private String fullname;
	public CommentUserResponse(User user) {
		super();
		this.userId = user.getUserId();
		this.fullname = user.getFullName();
	}
	
	
}
