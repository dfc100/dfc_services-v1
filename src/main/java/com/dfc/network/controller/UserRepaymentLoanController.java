package com.dfc.network.controller;

import com.dfc.network.dto.UserLoanRepaymentDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.service.impl.UserRepaymentLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserRepaymentLoanController {

    @Autowired
    private UserRepaymentLoanService userRepaymentLoanService;

    @GetMapping(value = "/get_loan_schedule")
    public ResponseEntity<List<UserLoanRepaymentDto>> getLoanSchedule(@RequestParam("user_id") final Integer userId) throws CustomMessageException{
        List<UserLoanRepaymentDto> userLoanEligibilityDtos;
        try {
            userLoanEligibilityDtos = userRepaymentLoanService.getLoanSchedule(userId);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userLoanEligibilityDtos, HttpStatus.OK);
    }

}
