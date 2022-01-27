package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.deleteuser.DeleteUserRequest;
import bookstorejwt.pos.com.deleteuser.DeleteUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DeleteUserService {

    public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) {
        log.info("[{}] -> deleteUser", this.getClass().getSimpleName());

        DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
        deleteUserResponse.setResponse("");

        return deleteUserResponse;
    }
}
