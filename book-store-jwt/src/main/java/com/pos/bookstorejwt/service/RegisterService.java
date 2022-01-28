package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.register.RegisterRequest;
import bookstorejwt.pos.com.register.RegisterResponse;
import com.pos.bookstorejwt.repository.User;
import com.pos.bookstorejwt.repository.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserService userService;

    public RegisterResponse register(RegisterRequest registerRequest) {
        log.info("[{}] -> register", this.getClass().getSimpleName());

        User user = userService.registerNewUser(registerRequest.getUsername(), registerRequest.getPassword());

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setResponse(String.format("User-ul %s a fost creat cu succes", user.getUsername()));

        return registerResponse;
    }
}
