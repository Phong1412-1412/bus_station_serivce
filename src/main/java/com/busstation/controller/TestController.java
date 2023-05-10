package com.busstation.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busstation.utils.SecurityUtils;

@RestController
public class TestController {
	


	@GetMapping("/hi")
	public String hi(OAuth2AuthenticationToken authenticationToken) {
		return "Hi " + SecurityUtils.getUserName();
	}
	
	@GetMapping("/user")
	public OAuth2User getUserInfo(OAuth2AuthenticationToken authenticationToken) {
		OAuth2User user = authenticationToken.getPrincipal();
		return user;
	}
}
