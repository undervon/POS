package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.token.DestroyTokenRequest;
import bookstorejwt.pos.com.token.DestroyTokenResponse;
import bookstorejwt.pos.com.token.ValidateTokenRequest;
import bookstorejwt.pos.com.token.ValidateTokenResponse;
import com.pos.bookstorejwt.jwt.JwtTokenUtil;
import com.pos.bookstorejwt.repository.Role;
import com.pos.bookstorejwt.repository.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    private static final List<String> blackList = new ArrayList<>();

    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) {
        log.info("[{}] -> validateToken", this.getClass().getSimpleName());

        String token = validateTokenRequest.getToken();

        if (blackList.contains(token)) {
            throw new RuntimeException("Tokenul este in black list");
        }

        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new RuntimeException("Tokenul este expirat");
        }

        String username = jwtTokenUtil.getUsernameFromToken(token);
        Role role = jwtTokenUtil.getRoleFromToken(token);

        // Check user by username if exist in DB
        userService.findByUsername(username);

        ValidateTokenResponse validateTokenResponse = new ValidateTokenResponse();
        validateTokenResponse.setUsername(username);
        validateTokenResponse.setRole(role.toString());

        return validateTokenResponse;
    }

    public DestroyTokenResponse destroyToken(DestroyTokenRequest destroyTokenRequest) {
        log.info("[{}] -> destroyToken", this.getClass().getSimpleName());

        String token = destroyTokenRequest.getToken();

        blackList.add(token);

        DestroyTokenResponse destroyTokenResponse = new DestroyTokenResponse();
        destroyTokenResponse.setResponse("Token distrus cu succes");

        return destroyTokenResponse;
    }
}
