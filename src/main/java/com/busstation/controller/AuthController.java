package com.busstation.controller;

import com.busstation.controller.verifyToken.VerificationToken;
import com.busstation.entities.User;
import com.busstation.event.RegistrationCompleteEvent;
import com.busstation.payload.request.EmployeeRequest;
import com.busstation.payload.request.LoginRequest;
import com.busstation.payload.request.SignupRequest;
import com.busstation.payload.response.JwtResponse;
import com.busstation.repositories.VerificationTokenRepository;
import com.busstation.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController(value = "authAPIofWeb")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;

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
        VerificationToken theToken = tokenRepository.findByToken(token);
        if(theToken.getUser().getStatus()) {
            return "This account has already been verified, please, login.";
        }
        String verificationResult = authService.validateToken(token);
        if(verificationResult.equalsIgnoreCase("valid")) {
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification token";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" +request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
