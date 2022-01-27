package com.pos.bookstorejwt.controller;

import bookstorejwt.pos.com.login.LoginRequest;
import bookstorejwt.pos.com.login.LoginResponse;
import com.pos.bookstorejwt.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Log4j2
@Endpoint
@RequiredArgsConstructor
public class LoginController {

    private static final String NAMESPACE_URI = "http://com.pos.bookstorejwt/login";

    private final LoginService loginService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest loginRequest) {
        log.info("[{}] -> login", this.getClass().getSimpleName());

        return loginService.login(loginRequest);
    }
}
