package com.dfc.network.controller;

import com.dfc.network.dto.UserDto;
import com.dfc.network.dto.UserLoanDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.service.LoanAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LoanAdminController {

    @Autowired
    private LoanAdminService loanAdminService;

    @GetMapping(value = "/get_admin_loans")
    public ResponseEntity<List<UserLoanDto>> getAdminLoans(@RequestParam("loan_status") final String loanStatus) throws CustomMessageException{
        List<UserLoanDto> userLoanDtos;
        try {
            userLoanDtos = loanAdminService.getAdminLoans(loanStatus);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userLoanDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/get_loans_by_user_id")
    public ResponseEntity<List<UserLoanDto>> getLoansByUserId(@RequestParam("user_id") final Integer userId) throws CustomMessageException{
        List<UserLoanDto> userLoanDtos;
        try {
            userLoanDtos = loanAdminService.getLoansByUserId(userId);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userLoanDtos, HttpStatus.OK);
    }

    @PostMapping("/confirm_loan")
    public ResponseEntity<UserLoanDto> confirmLoan(@RequestBody UserLoanDto userLoanDto) throws CustomMessageException{
        UserLoanDto userLoanDtoResponse;
        try {
            userLoanDtoResponse = loanAdminService.confirmLoan(userLoanDto);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userLoanDtoResponse, HttpStatus.OK);

    }



}
