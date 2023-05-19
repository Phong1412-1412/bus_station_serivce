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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.busstation.config.filter.JwtAuthTokenFilter;
import com.busstation.services.securityimpl.UserDetailServiceSecurityImpl;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private UserDetailsService userService;
	
// 	 @Autowired
//   private JwtProviderUtils jwtProvider;

    @Autowired
    private UserDetailServiceSecurityImpl userService;



    @Autowired
    private LogoutHandler logoutHandler;
         
    private static final String[] UN_SECURED_URLs = {
            "/api/v1/auth/**",
            "/api/v1/oauth2/**",
            "/chair-booking/**",
            "/comments/**",
            "/chair-booking"
    };

    private static final String[] HTTP_METHOD_GET_UN_SECURED_URLs = {
            "/api/v1/trips/search/**",
            "/api/v1/trips/getAll",
            "/api/v1/provinces/**",
            "/api/v1/chairs/**",
            "/api/v1/typeCar/**",
            "/api/v1/trips/**",
            "/api/v1/payments/all",
            "/api/v1/orderdetails/count/**",
            "/api/v1/orders/findCancel/**",
            "/api/v1/ratings"
    };

    private static final String[] HTTP_METHOD_POST_UN_SECURED_URLs = {
            "/api/v1/trips/search/**",            
    };

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
        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers(UN_SECURED_URLs).permitAll()
                .requestMatchers(HttpMethod.GET,HTTP_METHOD_GET_UN_SECURED_URLs).permitAll()
                .requestMatchers(HttpMethod.POST,HTTP_METHOD_POST_UN_SECURED_URLs).permitAll()
                .anyRequest()
                .authenticated().and().authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class).logout()
                .logoutUrl("/api/v1/auth/logout").addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()).and()
                .httpBasic(withDefaults());
                //.sessionManagement().sessionCreationPolicy(STATELESS)


        return http.build();
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
