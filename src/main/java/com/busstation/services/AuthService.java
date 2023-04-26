package com.busstation.services;

import com.busstation.controller.verifyToken.VerificationToken;
import com.busstation.entities.User;
import com.busstation.payload.request.EmployeeRequest;
import com.busstation.payload.request.LoginRequest;
import com.busstation.payload.request.SignupRequest;
import com.busstation.payload.response.ApiResponse;
import com.busstation.payload.response.JwtResponse;


public interface AuthService {
    JwtResponse signin(LoginRequest loginRequest);
    ApiResponse signUpUser(SignupRequest signupRequest);
    ApiResponse signUpEmployee(String accountId, EmployeeRequest employeeRequest);
    ApiResponse signUpForEmployees( SignupRequest signupRequest);
    //--------------------------------------------------------------------------------
    User registerUser(SignupRequest signupRequest);

    void saveUseVerificationToken(User theUser, String verificationToken);

    String validateToken(String theToken);

    VerificationToken generateNewVerificationToken(String oldToken);
}
