package com.busstation.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busstation.controller.verifyToken.VerificationToken;
import com.busstation.entities.Account;
import com.busstation.entities.User;
import com.busstation.payload.response.JwtResponse;
import com.busstation.repositories.AccountRepository;
import com.busstation.repositories.UserRepository;
import com.busstation.repositories.VerificationTokenRepository;
import com.busstation.services.GoogleLoginService;
import com.busstation.utils.JwtProviderUtils;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/oauth2")
@Slf4j
public class GoogleLoginController {

	@Autowired
	private JwtProviderUtils tokenProvider;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired UserRepository userRepository;

	@Autowired
	private GoogleLoginService googleLoginService;

	@PostMapping("/google-login")
	public ResponseEntity<JwtResponse> googleLogin(@RequestBody GoogleLoginRequest loginRequest) {
	    String email = loginRequest.getEmail();
	    String fullname = loginRequest.getFullname();

	    createAccLoginGoogle(email, fullname);
	    JwtResponse token = gettoken(email);
	    return ResponseEntity.ok(token);
	        
	}
	private JwtResponse gettoken(String email) {

		String jwt = tokenProvider.generateTokenUsingUserName(email);

		var account = accountRepository.findByusername(email);
		tokenProvider.revokeAllUserTokens(account);
		tokenProvider.saveUserToken(account, jwt);

		JwtResponse jwtResponse = new JwtResponse(jwt);

		return jwtResponse;
	}
	
	private void createAccLoginGoogle(String email, String fullname) {		
	    Account account = accountRepository.findByusername(email);
	    System.out.println(email);
		if (account != null) {
			log.info("Login goole : update account...");
			User existingUser = account.getUser();
			if(!existingUser.getStatus()) {
				log.info("Login goole : delete info account status false...");
				VerificationToken verificationToken = verificationTokenRepository.findByUser_UserId(existingUser.getUserId());
				verificationTokenRepository.delete(verificationToken);
				
				existingUser.setFullName(fullname);
				existingUser.setAddress("");
				existingUser.setPhoneNumber("");
				existingUser.setStatus(Boolean.TRUE);
				userRepository.save(existingUser);							
				account.setPassword(" ");
				accountRepository.save(account);
				return;
			}	
			return;
		}		
		log.info("Login goole : create new account...");
		googleLoginService.CreateNewUserloginWithGoogle(email, fullname);
		
	}
}
