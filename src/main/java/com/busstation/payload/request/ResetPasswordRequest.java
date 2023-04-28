package com.busstation.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResetPasswordRequest {

	private String email;
	private String newPassword;
	private String verifyNewPassword;
}
