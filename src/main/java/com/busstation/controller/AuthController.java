package com.busstation.controller;

import com.busstation.controller.verifyToken.VerificationToken;
import com.busstation.entities.User;
import com.busstation.event.RegistrationCompleteEvent;
import com.busstation.event.listener.RegistrationCompleteEventListener;
import com.busstation.payload.request.EmployeeRequest;
import com.busstation.payload.request.ForgotPasswordRequest;
import com.busstation.payload.request.LoginRequest;
import com.busstation.payload.request.ResetPasswordRequest;
import com.busstation.payload.request.SignupRequest;
import com.busstation.payload.response.JwtResponse;
import com.busstation.repositories.VerificationTokenRepository;
import com.busstation.services.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController(value = "authAPIofWeb")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    private final RegistrationCompleteEventListener eventListener;
    private final HttpServletRequest request;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.signin(loginRequest), HttpStatus.OK);
    }
    @PostMapping("/signupuser")
    public ResponseEntity<?> signUpUser(@RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authService.signUpUser(signupRequest),HttpStatus.CREATED);
    }
    @PostMapping("/signupemployee/{accountid}")
    public ResponseEntity<?> signUpEmployee(@PathVariable("accountid") String accountId,@RequestBody EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(authService.signUpEmployee(accountId,employeeRequest),HttpStatus.CREATED);
    }
    @PostMapping("/signupforemployee")
    public ResponseEntity<?> signUpForEmployees(@RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authService.signUpForEmployees(signupRequest),HttpStatus.CREATED);
    }
    //---------------------------------------------------------------------------------------------------------
    @PostMapping("signUpUserVerifyEmail")
    public String registerUser(@RequestBody SignupRequest signupRequest, final HttpServletRequest request) {
        User user = authService.registerUser(signupRequest);
        // Publish registration even
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success! Please, check you email for to complete you registration!";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        String url = applicationUrl(request)+"/api/v1/auth/resend-verification-token?token="+token;
        VerificationToken theToken = tokenRepository.findByToken(token);
        if(theToken.getUser().getStatus()) {
            return "This account has already been verified, please, login.";
        }
        String verificationResult = authService.validateToken(token);
        if(verificationResult.equalsIgnoreCase("valid")) {
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification link, <a href=\""+url+"\" > Get a new verification link. </a>";
    }

    @GetMapping("/resend-verification-token")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DRIVER', 'ROLE_ADMIN')")
    public String resendVerificationToken(@RequestParam("token") String oldToken, final HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = authService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendVerificationTokenEmail(theUser, applicationUrl(request), verificationToken);
        return "A new verification has been seen to your email. Please, check to activate you account";
    }

    private void resendVerificationTokenEmail(User theUser, String applicationUrl, VerificationToken verificationToken)
            throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/v1/auth/verifyEmail?token="+verificationToken.getToken();
        eventListener.sendVerificationEmail(url);
        log.info("Click the link to verify your registration :  {}", url);
    }

    @GetMapping("getVerificationToken/{userId}")
    public ResponseEntity<?> getVerificationToken(@PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(tokenRepository.findByUser_UserId(userId));
    }


    public String applicationUrl(HttpServletRequest request) {
        return "http://" +request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
    
    //forgot password verify email
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {

        return authService.forgotPassword(forgotPasswordRequest.getEmail());
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<String> reset_password(@RequestParam("token") String token,@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return authService.resetPasswordVerifyCode(
        		token, 
        		resetPasswordRequest.getEmail(), 
        		resetPasswordRequest.getNewPassword(), 
        		resetPasswordRequest.getVerifyNewPassword());
    }


}
