package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.deleteuser.DeleteUserRequest;
import bookstorejwt.pos.com.deleteuser.DeleteUserResponse;
import com.pos.bookstorejwt.repository.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DeleteUserService {

    private final UserService userService;

    public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) {
        log.info("[{}] -> deleteUser", this.getClass().getSimpleName());

        String username = deleteUserRequest.getUsername();

        userService.deleteUser(username);

        DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
        deleteUserResponse.setResponse(String.format("Userul %s a fost sters cu succes", username));

        return deleteUserResponse;
    }
}
