package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.adduser.AddUserRequest;
import bookstorejwt.pos.com.adduser.AddUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AddUserService {

    public AddUserResponse addUser(AddUserRequest addUserRequest) {
        log.info("[{}] -> addUser", this.getClass().getSimpleName());

        AddUserResponse addUserResponse = new AddUserResponse();
        addUserResponse.setResponse("");

        return addUserResponse;
    }
}
