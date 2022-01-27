package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.editpassworduser.EditPasswordUserRequest;
import bookstorejwt.pos.com.editpassworduser.EditPasswordUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class EditPasswordUserService {

    public EditPasswordUserResponse editPasswordUser(EditPasswordUserRequest editPasswordUserRequest) {
        log.info("[{}] -> editPasswordUser", this.getClass().getSimpleName());

        EditPasswordUserResponse editPasswordUserResponse = new EditPasswordUserResponse();
        editPasswordUserResponse.setResponse("");

        return editPasswordUserResponse;
    }
}
