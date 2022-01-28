package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.editpassworduser.EditPasswordUserRequest;
import bookstorejwt.pos.com.editpassworduser.EditPasswordUserResponse;
import com.pos.bookstorejwt.repository.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class EditPasswordUserService {

    private final UserService userService;

    public EditPasswordUserResponse editPasswordUser(EditPasswordUserRequest editPasswordUserRequest) {
        log.info("[{}] -> editPasswordUser", this.getClass().getSimpleName());

        String username = editPasswordUserRequest.getUsername();

        userService.editPasswordUser(username, editPasswordUserRequest.getOldPassword(),
                editPasswordUserRequest.getNewPassword());

        EditPasswordUserResponse editPasswordUserResponse = new EditPasswordUserResponse();
        editPasswordUserResponse.setResponse(String.format("Parola editata cu succes pentru userul %s", username));

        return editPasswordUserResponse;
    }
}
