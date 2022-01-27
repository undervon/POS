package com.pos.bookstorejwt.controller;

import bookstorejwt.pos.com.token.DestroyTokenRequest;
import bookstorejwt.pos.com.token.DestroyTokenResponse;
import bookstorejwt.pos.com.token.ValidateTokenRequest;
import bookstorejwt.pos.com.token.ValidateTokenResponse;
import com.pos.bookstorejwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Log4j2
@Endpoint
@RequiredArgsConstructor
public class TokenController {

    private static final String NAMESPACE_URI = "http://com.pos.bookstorejwt/token";

    private final TokenService tokenService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "validateTokenRequest")
    @ResponsePayload
    public ValidateTokenResponse validateToken(@RequestPayload ValidateTokenRequest validateTokenRequest) {
        log.info("[{}] -> validateToken", this.getClass().getSimpleName());

        return tokenService.validateToken(validateTokenRequest);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "destroyTokenRequest")
    @ResponsePayload
    public DestroyTokenResponse destroyToken(@RequestPayload DestroyTokenRequest destroyTokenRequest) {
        log.info("[{}] -> destroyToken", this.getClass().getSimpleName());

        return tokenService.destroyToken(destroyTokenRequest);
    }
}
