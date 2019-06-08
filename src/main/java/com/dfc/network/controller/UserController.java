package com.dfc.network.controller;

import com.dfc.network.dto.UserDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.model.User;
import com.dfc.network.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user_registration", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDto userDto) {
        try {
            userService.registerNewUserAccount(userDto);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Account created Successfully", HttpStatus.CREATED);

    }

    @GetMapping(value = "/get_user_details")
    public ResponseEntity<UserDto> getUserDetails(@RequestParam("id") final Integer userId) throws CustomMessageException {
        UserDto userDto;
        try {
            userDto = userService.getUserInfo(userId);
            if (userDto == null) {
                throw new CustomMessageException(HttpStatus.NOT_FOUND, "User Not Exists");
            }
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping(value = "/check_availability", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> checkAlreadyExists(@RequestBody UserDto userDto) {
        boolean response;
        try {
            response = userService.checkAlreadyExists(userDto);
        } catch (Exception ex) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PostMapping(value = "/login_user", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) throws CustomMessageException {
        UserDto userDtoResponse;
        try {
            userDtoResponse = userService.loginUser(userDto);
            if (userDtoResponse == null) {
                throw new CustomMessageException(HttpStatus.NOT_FOUND, "User Not Exists");
            }
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);

    }

    @PutMapping(value = "/update_user", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws CustomMessageException{
        UserDto userDtoResponse = null;
        try {
            userDtoResponse = userService.updateUser(userDto);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        if (userDtoResponse == null) {
            throw new CustomMessageException(HttpStatus.NOT_FOUND, "User Not Exists");
        }
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);

    }

    @PutMapping(value = "/change_password", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> changePassword(@RequestBody UserDto userDto) throws CustomMessageException{
        User user;
        try {
            user = userService.changePassword(userDto);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        if (user == null) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, "UnAble to change the Password. Contact Admin");
        }
        return new ResponseEntity<>("Password Changed Successfully", HttpStatus.OK);

    }

    @PostMapping(value = "/admin_user_registration", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> registerAdminUser(@RequestBody @Valid UserDto userDto) {
        try {
            userService.registerAdminNewUserAccount(userDto);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Account created Successfully", HttpStatus.CREATED);

    }


}
