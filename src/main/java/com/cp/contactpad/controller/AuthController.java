package com.cp.contactpad.controller;

import com.cp.contactpad.model.User;
import com.cp.contactpad.payload.ApiResponse;
import com.cp.contactpad.payload.AuthResponse;
import com.cp.contactpad.payload.LoginRequest;
import com.cp.contactpad.payload.SignUpRequest;
import com.cp.contactpad.service.auth.LoginServiceImpl;
import com.cp.contactpad.service.auth.SignUpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginServiceImpl loginService;
    private final SignUpServiceImpl signUpService;

    @Autowired
    public AuthController(
                          LoginServiceImpl loginService,
                          SignUpServiceImpl signUpService) {
        this.loginService = loginService;
        this.signUpService = signUpService;

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        String token = loginService.login(loginRequest);
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User result = signUpService.signUp(signUpRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getUser_id()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

}
