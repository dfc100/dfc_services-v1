package com.dfc.network.controller;

import com.dfc.network.dto.BinaryInfoDto;
import com.dfc.network.dto.SunFlowerInfoDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.service.UserTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserTreeController {

    @Autowired
    private UserTreeService userTreeService;

    @GetMapping(value = "/get_sunflower_details")
    public ResponseEntity<SunFlowerInfoDto> getSunFlowerDetails(@RequestParam("user_id") final Integer userId, @RequestParam("level") final Integer level) throws CustomMessageException{
        SunFlowerInfoDto sunFlowerInfoDto;
        try {
            sunFlowerInfoDto = userTreeService.getSunflowerDetails(userId, level);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        if (sunFlowerInfoDto == null) {
            throw new CustomMessageException(HttpStatus.NOT_FOUND, "User Not Exists");
        }
        return new ResponseEntity<>(sunFlowerInfoDto, HttpStatus.OK);
    }


    @GetMapping(value = "/get_binary_details")
    public ResponseEntity<BinaryInfoDto> getBinaryDetails(@RequestParam("user_id") final Integer userId, @RequestParam("level") final Integer level) throws CustomMessageException{
        BinaryInfoDto binaryInfoDto;
        try {
            binaryInfoDto = userTreeService.getBinaryDetails(userId, level);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        if (binaryInfoDto == null) {
            throw new CustomMessageException(HttpStatus.NOT_FOUND, "User Not Exists");
        }
        return new ResponseEntity<>(binaryInfoDto, HttpStatus.OK);
    }
}
