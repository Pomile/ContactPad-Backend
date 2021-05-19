package com.cp.contactpad.service.auth;

import com.cp.contactpad.payload.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    public String login(LoginRequest loginRequest);
}
