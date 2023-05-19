package com.busstation.payload.response;

import java.time.LocalDateTime;

import com.busstation.entities.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CommentResponse {

    private String commentId;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime createdAt;
    private ParentCommentResponse relyComment;
    private InfoUserResonse user;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.user = new InfoUserResonse(comment.getUser());
        if(comment.getParentComment() != null) {
        	this.relyComment = new ParentCommentResponse(comment.getParentComment());
        }
    }
}

@Data 
class ParentCommentResponse{	
	private String parentId;
	private String content;
	private InfoUserResonse user;
	public ParentCommentResponse(Comment comment) {
		super();
		this.parentId = comment.getCommentId();
		this.content = comment.getContent();
		this.user = new InfoUserResonse(comment.getUser());
	}	
}
