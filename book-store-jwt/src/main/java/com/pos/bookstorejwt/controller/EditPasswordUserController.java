package com.pos.bookstorejwt.controller;

import bookstorejwt.pos.com.editpassworduser.EditPasswordUserRequest;
import bookstorejwt.pos.com.editpassworduser.EditPasswordUserResponse;
import com.pos.bookstorejwt.service.EditPasswordUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Log4j2
@Endpoint
@RequiredArgsConstructor
public class EditPasswordUserController {

    private static final String NAMESPACE_URI = "http://com.pos.bookstorejwt/editPasswordUser";

    private final EditPasswordUserService editPasswordUserService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editPasswordUserRequest")
    @ResponsePayload
    public EditPasswordUserResponse editPasswordUser(@RequestPayload EditPasswordUserRequest editPasswordUserRequest) {
        log.info("[{}] -> editPasswordUser", this.getClass().getSimpleName());

        return editPasswordUserService.editPasswordUser(editPasswordUserRequest);
    }
}
