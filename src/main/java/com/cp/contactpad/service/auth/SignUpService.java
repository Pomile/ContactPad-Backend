package com.cp.contactpad.service.auth;

import com.cp.contactpad.model.User;
import com.cp.contactpad.payload.SignUpRequest;

public interface SignUpService {
    public User signUp(SignUpRequest signUpRequest);
}
