package com.dfc.network.controller;

import com.dfc.network.dto.UserLoanDto;
import com.dfc.network.dto.UserLoanEligibilityDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.model.UserLoan;
import com.dfc.network.service.UserLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserLoanController {

    @Autowired
    private UserLoanService userLoanService;

    @GetMapping(value = "/get_loan_eligibility")
    public ResponseEntity<List<UserLoanEligibilityDto>> getLoanEligibility(@RequestParam("user_id") final Integer userId) throws CustomMessageException{
        List<UserLoanEligibilityDto> userLoanEligibilityDtos;
        try {
            userLoanEligibilityDtos = userLoanService.getLoanEligibility(userId);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userLoanEligibilityDtos, HttpStatus.OK);
    }


    @GetMapping(value = "/get_loan_details")
    public ResponseEntity<List<UserLoanDto>> getLoanDetails(@RequestParam("user_id") final Integer userId) throws CustomMessageException{
        List<UserLoanDto> userLoanDtos;
        try {
            userLoanDtos = userLoanService.getLoanDetails(userId);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userLoanDtos, HttpStatus.OK);
    }


    @PostMapping(value = "/apply_loan", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserLoanDto> applyLoan(@RequestBody @Valid UserLoanEligibilityDto userLoanEligibilityDto) throws CustomMessageException{
        UserLoanDto userLoanDto;
        try {
            userLoanDto = userLoanService.applyLoan(userLoanEligibilityDto);
        } catch (CustomMessageException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userLoanDto, HttpStatus.OK);
    }
}
