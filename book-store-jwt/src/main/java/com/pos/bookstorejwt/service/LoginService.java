package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.login.LoginRequest;
import bookstorejwt.pos.com.login.LoginResponse;
import com.pos.bookstorejwt.jwt.JwtTokenUtil;
import com.pos.bookstorejwt.repository.User;
import com.pos.bookstorejwt.repository.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        log.info("[{}] -> login", this.getClass().getSimpleName());

        String username = loginRequest.getUsername();

        User user = userService.findByUsername(username);

        userService.checkPassword(username, loginRequest.getPassword());

        String token = jwtTokenUtil.generateToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);

        return loginResponse;
    }
}

