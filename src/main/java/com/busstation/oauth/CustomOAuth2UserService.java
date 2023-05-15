package com.busstation.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.busstation.controller.verifyToken.VerificationToken;
import com.busstation.entities.Account;
import com.busstation.entities.User;
import com.busstation.repositories.AccountRepository;
import com.busstation.repositories.UserRepository;
import com.busstation.repositories.VerificationTokenRepository;
import com.busstation.services.GoogleLoginService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired UserRepository userRepository;

	@Autowired
	private GoogleLoginService googleLoginService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(oAuth2UserRequest);
		String username = user.getAttribute("email");
		String fullName = user.getAttribute("name");
		
		Account account = accountRepository.findByusername(username);

		if (account != null) {
			log.info("Login goole : update account...");
			User existingUser = account.getUser();
			if(!existingUser.getStatus()) {
				log.info("Login goole : delete info account status false...");
				VerificationToken verificationToken = verificationTokenRepository.findByUser_UserId(existingUser.getUserId());
				verificationTokenRepository.delete(verificationToken);
				
				existingUser.setFullName(fullName);
				existingUser.setAddress("");
				existingUser.setPhoneNumber("");
				existingUser.setStatus(Boolean.TRUE);
				userRepository.save(existingUser);
								
				account.setPassword(" ");
				accountRepository.save(account);

				return new CustomOAuth2User(user);
			}						
			return new CustomOAuth2User(user);
		}
		
		log.info("Login goole : create new account...");
		googleLoginService.CreateNewUserloginWithGoogle(username, fullName);
		return new CustomOAuth2User(user);
	}
	
}
