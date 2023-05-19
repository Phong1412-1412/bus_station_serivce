package com.busstation.payload.response;

import java.time.LocalDateTime;

import com.busstation.entities.Rating;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RatingResponse {

	private String ratingId;
	
	private int rating;
	
	private String content;
	
	private boolean haveOrder = false;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime createdAt; 
	
	private InfoUserResonse user;
	
	public RatingResponse(Rating rating) {
		super();
		this.ratingId = rating.getRatingId();
		this.rating = rating.getRating();
		this.content = rating.getContent();
		this.createdAt = rating.getCreatedAt();	
		InfoUserResonse user = new InfoUserResonse(rating.getUser());
		this.user = user;
		if(rating.getUser().getOrders().size() != 0) {
			this.haveOrder = true;
		}
		
		
	}		
}
