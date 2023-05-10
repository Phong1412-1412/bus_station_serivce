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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime createdAt;
    private ParentCommentResponse parentComment;
    private CommentUserResponse user;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.user = new CommentUserResponse(comment.getUser());
        if(comment.getParentComment() != null) {
        	this.parentComment = new ParentCommentResponse(comment.getParentComment());
        }
    }
}

@Data 
class CommentUserResponse{
	
	private String userId;
	private String fullName;
	private String roleId;
	public CommentUserResponse(User user) {
		super();
		this.userId = user.getUserId();
		this.fullName = user.getFullName();
		this.roleId = user.getAccount().getRole().getRoleId();
	}	
}

@Data 
class ParentCommentResponse{	
	private String parentId;
	private String contentPrent;
	public ParentCommentResponse(Comment comment) {
		super();
		this.parentId = comment.getCommentId();
		this.contentPrent = comment.getContent();
	}	
}
