package com.busstation.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class SecurityUtils {

	public static String getUserName() {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (principal instanceof OAuth2User) {
	        return ((OAuth2User) principal).getAttribute("email");
	    } else if (principal instanceof UserDetails) {
	        return ((UserDetails) principal).getUsername();
	    } else {
	        return principal.toString();
	    }
	}
}
