package com.dfc.network.controller;

import com.dfc.network.dto.UserDto;
import com.dfc.network.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class UserSuperAdminController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/super_admin_user_registration", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> registerSuperAdminUser(@RequestBody @Valid UserDto userDto) {
        try {
            userService.registerSuperAdminNewUserAccount(userDto);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Account created Successfully", HttpStatus.CREATED);

    }

}
