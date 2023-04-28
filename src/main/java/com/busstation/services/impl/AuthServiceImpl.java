package com.busstation.services.impl;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.busstation.controller.verifyToken.VerificationToken;
import com.busstation.email.EmailService;
import com.busstation.entities.Account;
import com.busstation.entities.Employee;
import com.busstation.entities.Role;
import com.busstation.entities.Token;
import com.busstation.entities.User;
import com.busstation.enums.AuthenticationProvider;
import com.busstation.enums.NameRoleEnum;
import com.busstation.enums.TokenEnum;
import com.busstation.exception.DataExistException;
import com.busstation.exception.DataNotFoundException;
import com.busstation.payload.request.EmployeeRequest;
import com.busstation.payload.request.LoginRequest;
import com.busstation.payload.request.SignupRequest;
import com.busstation.payload.response.ApiResponse;
import com.busstation.payload.response.JwtResponse;
import com.busstation.repositories.AccountRepository;
import com.busstation.repositories.EmployeeRepository;
import com.busstation.repositories.RoleRepository;
import com.busstation.repositories.TokenRepository;
import com.busstation.repositories.UserRepository;
import com.busstation.repositories.VerificationTokenRepository;
import com.busstation.services.AuthService;
import com.busstation.utils.JwtProviderUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProviderUtils tokenProvider;

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	private final VerificationTokenRepository verificationTokenRepository;

	@Override
	public JwtResponse signin(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateTokenUsingUserName(loginRequest.getUsername());

		var account = accountRepository.findByusername(loginRequest.getUsername());
		revokeAllUserTokens(account);
		saveUserToken(account, jwt);

		return new JwtResponse(jwt);
	}

	@Override
	@Transactional
	public ApiResponse signUpUser(SignupRequest signupRequest) {

		String username = signupRequest.getUsername();
		if (accountRepository.existsByusername(username)) {
			throw new DataExistException("This user with username: " + username + " already exist");
		} else {
			Account account = new Account();
			account.setUsername(signupRequest.getUsername());
			account.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			Role role = roleRepository.findByName(NameRoleEnum.ROLE_USER.toString());
			account.setRole(role);
			accountRepository.save(account);
			User user = new User();
			user.setAccount(accountRepository.findById(account.getAccountId()).get());
			user.setFullName(signupRequest.getUser().getFullName());
			user.setPhoneNumber(signupRequest.getUser().getPhoneNumber());
			user.setEmail(signupRequest.getUser().getEmail());
			user.setAddress(signupRequest.getUser().getAddress());
			user.setStatus(Boolean.TRUE);
			user.setAuthProvider(AuthenticationProvider.LOCAL);
			userRepository.save(user);
		}
		return new ApiResponse("Create new user successfully", HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ApiResponse signUpEmployee(String accountId, EmployeeRequest employeeRequest) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new DataNotFoundException("Can't find this account"));
		Role role = roleRepository.findById(employeeRequest.getRoleId())
				.orElseThrow(() -> new DataNotFoundException("Can't find this role"));
		account.setRole(role);
		accountRepository.save(account);
		Employee employee = new Employee();
		employee.setDob(employeeRequest.getDob());
		employee.setYoe(employeeRequest.getYoe());
		employee.setUser(account.getUser());
		employeeRepository.save(employee);
		return new ApiResponse("Create successfully", HttpStatus.CREATED);
	}

	private void saveUserToken(Account account, String jwtToken) {
		Token token = new Token();
		token.setAccount(account);
		token.setToken(jwtToken);
		token.setExpired(false);
		token.setRevoked(false);
		token.setTokenType(TokenEnum.BEARER);
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(Account account) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(account.getAccountId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	@Override
	public ApiResponse signUpForEmployees(SignupRequest signupRequest) {
		String username = signupRequest.getUsername();
		String email = signupRequest.getUser().getEmail();

		if (accountRepository.existsByusername(username) && userRepository.existsByEmail(email)) {
			throw new DataExistException("This user with username or email already exist");
		} else {
			Account account = new Account();
			account.setUsername(signupRequest.getUsername());
			account.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			Role role = roleRepository.findByName(signupRequest.getRole());
			account.setRole(role);
			accountRepository.save(account);

			User user = new User();
			user.setAccount(accountRepository.findById(account.getAccountId()).get());
			user.setFullName(signupRequest.getUser().getFullName());
			user.setPhoneNumber(signupRequest.getUser().getPhoneNumber());
			user.setEmail(signupRequest.getUser().getEmail());
			user.setAddress(signupRequest.getUser().getAddress());
			user.setStatus(Boolean.TRUE);
			userRepository.save(user);

			Employee employee = new Employee();
			employee.setDob(signupRequest.getEmployee().getDob());
			employee.setYoe(signupRequest.getEmployee().getYoe());
			employee.setUser(user);
			employeeRepository.save(employee);
		}

		return new ApiResponse("Create employee successfully", HttpStatus.CREATED);
	}

//--------------------------------------------------------------------------------------------------------
	@Override
	public User registerUser(SignupRequest signupRequest) {
		String username = signupRequest.getUsername();
		String email = signupRequest.getUser().getEmail();
		User user = new User();

		if (accountRepository.existsByusername(username) && userRepository.existsByEmail(email)) {
			throw new DataExistException("This user with username or email already exist");
		} else {
			Account account = new Account();
			account.setUsername(signupRequest.getUsername());
			account.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			Role role = roleRepository.findByName(NameRoleEnum.ROLE_USER.toString());
			account.setRole(role);
			accountRepository.save(account);
			user.setAccount(accountRepository.findById(account.getAccountId()).get());
			user.setFullName(signupRequest.getUser().getFullName());
			user.setPhoneNumber(signupRequest.getUser().getPhoneNumber());
			user.setEmail(signupRequest.getUser().getEmail());
			user.setAddress(signupRequest.getUser().getAddress());
			user.setStatus(Boolean.FALSE);
		}
		return userRepository.save(user);
	}

	@Override
	public void saveUseVerificationToken(User theUser, String Token) {
		var verificationToken = new VerificationToken(Token, theUser);
		verificationTokenRepository.save(verificationToken);
	}

	@Override
	public String validateToken(String theToken) {
		VerificationToken token = verificationTokenRepository.findByToken(theToken);
		if (token == null) {
			return "Invalid verification token";
		}
		User user = token.getUser();
		Calendar calendar = Calendar.getInstance();
		if (token.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
			return "Token already expired";
		}
		user.setStatus(true);
		userRepository.save(user);
		return "valid";
	}

	@Override
	public VerificationToken generateNewVerificationToken(String oldToken) {
		VerificationToken token = verificationTokenRepository.findByToken(oldToken);
		VerificationToken tokenTime = new VerificationToken();
		token.setToken(UUID.randomUUID().toString());
		token.setExpirationTime(tokenTime.getExpirationTime());
		return verificationTokenRepository.save(token);
	}

	@Override
	public ResponseEntity<String> forgotPassword(String email) {
		try {
			User user = userRepository.findByEmail(email);
			if (user == null) {
				return ResponseEntity.badRequest().body("User with email " + email + " not found");
			} else {
				String verificationCode = UUID.randomUUID().toString();
				user.setResetPasswordToken(verificationCode);
				userRepository.save(user);

				// sendForgotPasswordEmail(user.getEmail(), verificationCode);
				String subject = "Forgot Password Verification Code";
				String resetPasswordUrl = "http://localhost:9999/api/v1/auth/reset-password?token=" + verificationCode;
				String content = "<html>"
			               + "<body style=\"background-color: #f2f2f2; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 16px; line-height: 1.5;\">"
			               + "<div style=\"max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
			               + "<p>Hi, "+user.getFullName()+"</p>"
			               + "<p>You have requested to reset your password on our website.</p>"
			               + "<p>Please click the button below to reset your password:</p>"
			               + "<p style=\"text-align: center;\">"
			               + "<a href=\'"+resetPasswordUrl+"\' style=\"display: inline-block; background-color: #0074D9; color: #fff; text-decoration: none; padding: 10px 20px; border-radius: 5px; font-size: 18px; font-weight: bold; text-align: center;\">Reset Your Password</a>"
			               + "</p>"
			               + "<p>If you did not request this password reset, please ignore this email.</p>"
			               + "<p>If you need help, please visit our <a href=\"#\">help center</a>.</p>"
			               + "<p>Thank you for using our service.</p>"
			               + "</div>"
			               + "</body>"
			               + "</html>";

				emailService.sendForgotPasswordEmail(email, verificationCode, subject, content);

				return ResponseEntity.ok().body("Verification code sent to " + user.getEmail());
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<String> resetPasswordVerifyCode(String code, String email, String newPassword,
			String verifyPassword) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			return ResponseEntity.badRequest().body("User with email " + email + " not found");
		}

		if (!user.getResetPasswordToken().equals(code)) {
			return ResponseEntity.badRequest().body("Invalid verification code");
		}
		if (!newPassword.equals(verifyPassword)) {
			return ResponseEntity.badRequest()
					.body("Your new password and verification password do not match. Please check and try again.");
		}

		user.setResetPasswordToken(null);
		userRepository.save(user);
		Account account = accountRepository.findByusername(user.getAccount().getUsername());
		account.setPassword(passwordEncoder.encode(newPassword));
		accountRepository.save(account);

		return ResponseEntity.ok().body("Password reset successfully");
	}

}
