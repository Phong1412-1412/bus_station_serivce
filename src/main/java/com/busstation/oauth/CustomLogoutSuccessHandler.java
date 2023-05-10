package com.busstation.oauth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
    	SecurityContextHolder.clearContext();
        SecurityContextHolder.createEmptyContext();
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        System.out.println("Logout.....");
        
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Logout Success");
        response.getWriter().flush();
        response.getWriter().close();
    }

}