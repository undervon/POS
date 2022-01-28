package com.pos.bookstorejwt.service;

import bookstorejwt.pos.com.editroleuser.EditRoleUserRequest;
import bookstorejwt.pos.com.editroleuser.EditRoleUserResponse;
import com.pos.bookstorejwt.repository.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class EditRoleUserService {

    private final UserService userService;

    public EditRoleUserResponse editRoleUser(EditRoleUserRequest editRoleUserRequest) {
        log.info("[{}] -> editRoleUser", this.getClass().getSimpleName());

        String username = editRoleUserRequest.getUsername();

        userService.editRoleUser(username, editRoleUserRequest.getNewRole());

        EditRoleUserResponse editRoleUserResponse = new EditRoleUserResponse();
        editRoleUserResponse.setResponse(String.format("Rolul userului %s editat cu succes", username));

        return editRoleUserResponse;
    }
}
