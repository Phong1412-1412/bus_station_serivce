package com.busstation.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.busstation.config.filter.JwtAuthTokenFilter;
import com.busstation.oauth.CustomAuthenticationSuccessHandler;
import com.busstation.oauth.CustomLogoutSuccessHandler;
import com.busstation.services.securityimpl.UserDetailServiceSecurityImpl;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailServiceSecurityImpl userService;

	@Autowired
	private LogoutHandler logoutHandler;

	private static final String[] UN_SECURED_URLs = { "/api/v1/auth/**", "/chair-booking/**", "/chair-booking",
			"/api/v1/auth/forgot-password", "/api/v1/auth/reset-password"

	};

	private static final String[] HTTP_METHOD_GET_UN_SECURED_URLs = { "/api/v1/trips/search/**", "/api/v1/trips/getAll",
			"/api/v1/provinces/**", "/api/v1/chairs/**" };

	private static final String[] HTTP_METHOD_POST_UN_SECURED_URLs = { "/api/v1/trips/search/**", };

	@Bean
	public JwtAuthTokenFilter jwtAuthTokenFilter() {
		return new JwtAuthTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable().authorizeHttpRequests().requestMatchers(UN_SECURED_URLs).permitAll()
				.requestMatchers(HttpMethod.GET, HTTP_METHOD_GET_UN_SECURED_URLs).permitAll()
				.requestMatchers(HttpMethod.POST, HTTP_METHOD_POST_UN_SECURED_URLs).permitAll().anyRequest()
				.authenticated().and().authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class).logout()
				.logoutUrl("/api/v1/auth/logout").addLogoutHandler(logoutHandler)
				.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()).and()
				.httpBasic(withDefaults());
		// .sessionManagement().sessionCreationPolicy(STATELESS)
//        	    .oauth2Login(o -> o
//        	    		.userInfoEndpoint()
//    					.userService(customOAuth2UserService)
//    					.and()
//        	            .successHandler(successHandler())
//        	     )
//        	    .logout(l -> l
//        	    		.logoutUrl("/logout")
//        	    		.logoutSuccessHandler(logoutSuccessHandler())
//        	    		.deleteCookies("JSESSIONID")
//                        .invalidateHttpSession(true)
//                        .permitAll()
//        	    );

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomAuthenticationSuccessHandler("/api/v1/accounts/information");
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
