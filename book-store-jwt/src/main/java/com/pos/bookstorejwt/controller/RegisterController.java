package com.pos.bookstorejwt.controller;

import bookstorejwt.pos.com.register.RegisterRequest;
import bookstorejwt.pos.com.register.RegisterResponse;
import com.pos.bookstorejwt.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Log4j2
@Endpoint
@RequiredArgsConstructor
public class RegisterController {

    private static final String NAMESPACE_URI = "http://com.pos.bookstorejwt/register";

    private final RegisterService registerService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerRequest")
    @ResponsePayload
    public RegisterResponse register(@RequestPayload RegisterRequest registerRequest) {
        log.info("[{}] -> register", this.getClass().getSimpleName());

        return registerService.register(registerRequest);
    }
}
