package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.editpassworduser.EditPasswordUserRequest;
import bookstorejwt.pos.com.editroleuser.EditRoleUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class EditRoleUserService {

    public EditRoleUserResponse editRoleUser(EditPasswordUserRequest editRoleUserRequest) {
        log.info("[{}] -> editRoleUser", this.getClass().getSimpleName());

        EditRoleUserResponse editRoleUserResponse = new EditRoleUserResponse();
        editRoleUserResponse.setResponse("");

        return editRoleUserResponse;
    }
}
