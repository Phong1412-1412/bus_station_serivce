package com.busstation.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GetUserUtil {
    public  String GetUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username="";
        if (principal instanceof OAuth2User) {
        	username = ((OAuth2User) principal).getAttribute("email");
	    } else if (principal instanceof UserDetails) {
	    	username = ((UserDetails) principal).getUsername();
	    } else {
	        return principal.toString();
	    }
        return username;
    }
}
