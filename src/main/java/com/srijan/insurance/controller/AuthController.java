package com.srijan.insurance.controller;

import com.srijan.insurance.dto.LoginRequest;
import com.srijan.insurance.dto.LoginResponse;
import com.srijan.insurance.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),
                        request.getPassword()

                )
        );

        String token = jwtUtil.generateToken(
                request.getEmail()
        );

        return new LoginResponse(token);
    }
}