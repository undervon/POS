package com.pos.bookstorejwt.controller;

import bookstorejwt.pos.com.editroleuser.EditRoleUserRequest;
import bookstorejwt.pos.com.editroleuser.EditRoleUserResponse;
import com.pos.bookstorejwt.service.EditRoleUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Log4j2
@Endpoint
@RequiredArgsConstructor
public class EditRoleUserController {

    private static final String NAMESPACE_URI = "http://com.pos.bookstorejwt/editRoleUser";

    private final EditRoleUserService editRoleUserService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editRoleUserRequest")
    @ResponsePayload
    public EditRoleUserResponse editRoleUser(@RequestPayload EditRoleUserRequest editRoleUserRequest) {
        log.info("[{}] -> editRoleUser", this.getClass().getSimpleName());

        return editRoleUserService.editRoleUser(editRoleUserRequest);
    }
}
