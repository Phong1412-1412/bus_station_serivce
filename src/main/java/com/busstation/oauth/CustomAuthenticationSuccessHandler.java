package com.busstation.oauth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.busstation.services.GoogleLoginService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private GoogleLoginService googleLoginService;

	private final String defaultTargetUrl;

	public CustomAuthenticationSuccessHandler(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

		String email = oauthToken.getPrincipal().getAttribute("email");
		String name = oauthToken.getPrincipal().getAttribute("name");

		// add user to database

		googleLoginService.loginWithGoogle(email, name);

		// Redirect to "name" page
		super.setDefaultTargetUrl(defaultTargetUrl);
		super.onAuthenticationSuccess(request, response, authentication);

	}
}