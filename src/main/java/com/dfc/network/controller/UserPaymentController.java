package com.dfc.network.controller;

import com.dfc.network.dto.ConfirmPaymentDto;
import com.dfc.network.dto.UserPaymentDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.model.UserPayment;
import com.dfc.network.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserPaymentController {

    @Autowired
    private UserPaymentService userPaymentService;

    @GetMapping(value = "/get_mentor_fee_list")
    public ResponseEntity<List<UserPaymentDto>> getMentorFeeList(@RequestParam("user_id") final Integer userId) throws CustomMessageException{
        List<UserPaymentDto> userPaymentDtos;
        try {
            userPaymentDtos = userPaymentService.getMentorFeeList(userId);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userPaymentDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/get_mentor_payments")
    public ResponseEntity<List<UserPaymentDto>> getMentorPayments(@RequestParam("user_id") final Integer userId, @RequestParam("paid_status") final String paidStatus) throws CustomMessageException{
        List<UserPaymentDto> userPaymentDtos;
        try {
            userPaymentDtos = userPaymentService.getMentorPayments(userId, paidStatus);
        } catch (Exception ex) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(userPaymentDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/confirm_mentor_fee", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> confirmPayment(@RequestBody @Valid ConfirmPaymentDto confirmPaymentDto) throws CustomMessageException{
        UserPayment userPayment;
        try {
            userPayment = userPaymentService.confirmPayment(confirmPaymentDto);
            if (null == userPayment) {
                throw new CustomMessageException(HttpStatus.NOT_FOUND, "The record could not be found. Please contact Admin");
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("The transaction confirmed successfully", HttpStatus.OK);

    }

}
