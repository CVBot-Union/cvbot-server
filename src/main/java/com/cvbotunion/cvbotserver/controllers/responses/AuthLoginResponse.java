package com.cvbotunion.cvbotserver.controllers.responses;

import java.io.Serializable;

public class AuthLoginResponse implements Serializable {

    private final String token;

    public AuthLoginResponse(String token) {
        this.token = token;
    }

    public String getToken() { return this.token; }
}
