package com.pos.bookstorejwt.controller;

import bookstorejwt.pos.com.adduser.AddUserRequest;
import bookstorejwt.pos.com.adduser.AddUserResponse;
import com.pos.bookstorejwt.service.AddUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Log4j2
@Endpoint
@RequiredArgsConstructor
public class AddUserController {

    private static final String NAMESPACE_URI = "http://com.pos.bookstorejwt/addUser";

    private final AddUserService addUserService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest addUserRequest) {
        log.info("[{}] -> addUser", this.getClass().getSimpleName());

        return addUserService.addUser(addUserRequest);
    }
}
