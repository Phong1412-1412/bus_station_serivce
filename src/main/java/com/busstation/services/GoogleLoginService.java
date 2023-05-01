package com.busstation.services;

public interface GoogleLoginService {
	
	public void CreateNewUserloginWithGoogle(String email, String fullname);

	public void UpdateUserloginWithGoogle(String accountId, String fullName);
}
