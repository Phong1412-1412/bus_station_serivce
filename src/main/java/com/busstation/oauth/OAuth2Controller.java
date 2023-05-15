package com.busstation.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busstation.payload.response.JwtResponse;
import com.busstation.repositories.AccountRepository;
import com.busstation.utils.JwtProviderUtils;
import com.busstation.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {
	
	@Autowired
	private JwtProviderUtils tokenProvider;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping("/accessToken")
	public JwtResponse gettoken() {

		String email = SecurityUtils.getUserName();

		String jwt = tokenProvider.generateTokenUsingUserName(email);
		
		var account = accountRepository.findByusername(email);
		tokenProvider.revokeAllUserTokens(account);
		tokenProvider.saveUserToken(account, jwt);
		
		JwtResponse jwtResponse = new JwtResponse(jwt);
		
		return jwtResponse;
	}

}
