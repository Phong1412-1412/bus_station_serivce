package com.busstation.payload.response;

import com.busstation.entities.User;

import lombok.Data;

@Data
public class InfoUserResonse {
	private String userId;
	private String fullName;
	private String roleId;
	public InfoUserResonse(User user) {
		super();
		this.userId = user.getUserId();
		this.fullName = user.getFullName();
		this.roleId = user.getAccount().getRole().getRoleId();
	}	

}
