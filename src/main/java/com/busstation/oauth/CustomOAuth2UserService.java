package com.busstation.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.busstation.entities.Account;
import com.busstation.entities.User;
import com.busstation.repositories.AccountRepository;
import com.busstation.repositories.UserRepository;
import com.busstation.services.GoogleLoginService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private GoogleLoginService googleLoginService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);
		String googleId = user.getAttribute("sub");
		String email = user.getAttribute("sub");
		String fullName = user.getAttribute("name");
		
		Account account = accountRepository.findByusername(googleId);

		if (account != null) {
			System.out.println("Updating.....");	
			googleLoginService.UpdateUserloginWithGoogle(account.getAccountId(), fullName);
			return new CustomOAuth2User(user);
		}
		System.out.println("Creating....");

		googleLoginService.CreateNewUserloginWithGoogle(googleId, email, fullName);

		return new CustomOAuth2User(user);
	}

}
