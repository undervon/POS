package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.adduser.AddUserRequest;
import bookstorejwt.pos.com.adduser.AddUserResponse;
import com.pos.bookstorejwt.repository.User;
import com.pos.bookstorejwt.repository.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AddUserService {

    private final UserService userService;

    public AddUserResponse addUser(AddUserRequest addUserRequest) {
        log.info("[{}] -> addUser", this.getClass().getSimpleName());

        User user = userService.createNewUser(addUserRequest.getUsername(), addUserRequest.getPassword(),
                addUserRequest.getRole());

        AddUserResponse addUserResponse = new AddUserResponse();
        addUserResponse.setResponse(String.format("Userul %s cu rolul %s a fost creat cu succes", user.getUsername(),
                user.getRole()));

        return addUserResponse;
    }
}
