package com.pos.bookstorejwt.controller;

import bookstorejwt.pos.com.deleteuser.DeleteUserRequest;
import bookstorejwt.pos.com.deleteuser.DeleteUserResponse;
import com.pos.bookstorejwt.service.DeleteUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Log4j2
@Endpoint
@RequiredArgsConstructor
public class DeleteUserController {

    private static final String NAMESPACE_URI = "http://com.pos.bookstorejwt/deleteUser";

    private final DeleteUserService deleteUserService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest deleteUserRequest) {
        log.info("[{}] -> deleteUser", this.getClass().getSimpleName());

        return deleteUserService.deleteUser(deleteUserRequest);
    }
}
