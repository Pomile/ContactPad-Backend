package com.cp.contactpad.payload;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType;

    public AuthResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;

    }

    // Getters and Setters (Omitted for brevity)
}
