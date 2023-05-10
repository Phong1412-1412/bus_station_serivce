package com.busstation.oauth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.busstation.payload.response.JwtResponse;
import com.busstation.repositories.AccountRepository;
import com.busstation.utils.JwtProviderUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private JwtProviderUtils tokenProvider;
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
		String email = oauth2User.getAttribute("email");

		String jwt = tokenProvider.generateTokenUsingUserName(email);
		
		var account = accountRepository.findByusername(email);
		tokenProvider.revokeAllUserTokens(account);
		tokenProvider.saveUserToken(account, jwt);
		
		JwtResponse jwtResponse = new JwtResponse(jwt);

		ObjectMapper mapper = new ObjectMapper();
		String jwtResponseJson = mapper.writeValueAsString(jwtResponse);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		byte[] jwtResponseBytes = jwtResponseJson.getBytes("UTF-8");

		response.getOutputStream().write(jwtResponseBytes);
		response.flushBuffer();

	}
}