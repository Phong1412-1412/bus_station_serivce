package com.busstation.oauth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {



	private final String defaultTargetUrl;

	public CustomAuthenticationSuccessHandler(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		super.setDefaultTargetUrl(defaultTargetUrl);
		super.onAuthenticationSuccess(request, response, authentication);

	}
}