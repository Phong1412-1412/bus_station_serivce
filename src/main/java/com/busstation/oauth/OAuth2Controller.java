package com.busstation.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {
	
	@GetMapping("/authorization/{registrationId}")
	public String authorize(@PathVariable("registrationId") String registrationId, HttpServletRequest request) {
	    String redirectUri = UriComponentsBuilder.fromHttpUrl("http://localhost:9999/oauth2/authorization/" + registrationId)
	            .queryParam("redirect_uri", "{baseUrl}/oauth2/callback/{registrationId}")
	            .buildAndExpand(getBaseUrl(request), registrationId)
	            .toUriString();
	    return "redirect:" + redirectUri;
	}
	
	private String getBaseUrl(HttpServletRequest request) {
	    StringBuffer url = request.getRequestURL();
	    String uri = request.getRequestURI();
	    return url.substring(0, url.length() - uri.length()) + request.getContextPath();
	}

}
