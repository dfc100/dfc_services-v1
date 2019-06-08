package com.dfc.network.service;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.ConfirmPaymentDto;
import com.dfc.network.dto.UserPaymentDto;
import com.dfc.network.helper.UserPaymentHelper;
import com.dfc.network.model.UserPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserPaymentService {

    @Autowired
    private UserPaymentHelper userPaymentHelper;

    @Autowired
    private FileStorageService fileStorageService;

    public List<UserPaymentDto> getMentorFeeList(Integer userId) {
        List<UserPayment> userPayments = userPaymentHelper.findBySenderUserId(userId);
        List<UserPaymentDto> userPaymentDtos = new ArrayList<>();
        Optional.ofNullable(userPayments).ifPresent(userPaymentList -> userPaymentList.forEach(userPayment -> {
            UserPaymentDto userPaymentDto = new UserPaymentDto();
            setUserPaymentDto(userPayment,userPaymentDto);
            userPaymentDtos.add(userPaymentDto);
        }));

        return userPaymentDtos;
    }


    public List<UserPaymentDto> getMentorPayments(Integer userId) {
        List<UserPayment> userPayments = userPaymentHelper.findByReceiverUserId(userId);
        List<UserPaymentDto> userPaymentDtos = new ArrayList<>();
        Optional.ofNullable(userPayments).ifPresent(userPaymentList -> userPaymentList.forEach(userPayment -> {
            UserPaymentDto userPaymentDto = new UserPaymentDto();
            setUserPaymentDto(userPayment,userPaymentDto);
            userPaymentDtos.add(userPaymentDto);
        }));

        return userPaymentDtos;
    }


    private void setUserPaymentDto(UserPayment userPayment, UserPaymentDto userPaymentDto) {
        userPaymentDto.setConfirmationDate(userPayment.getConfirmationDate());
        userPaymentDto.setUserPaymentId(userPayment.getUserPaymentId());
        userPaymentDto.setConfirmationStatus(userPayment.getConfirmationStatus());
        userPaymentDto.setConfirmedBy(userPayment.getConfirmedBy());
        userPaymentDto.setLevel(userPayment.getLevel());
        userPaymentDto.setPaidDate(userPayment.getPaidDate());
        userPaymentDto.setPaidStatus(userPayment.getPaidStatus());
        userPaymentDto.setReceiverId(userPayment.getReceiver().getUserId());
        userPaymentDto.setScreenshotPath(userPayment.getScreenshotPath());
        userPaymentDto.setSenderId(userPayment.getSender().getUserId());
        userPaymentDto.setValue(userPayment.getValue());
        userPaymentDto.setReceiverFullName(userPayment.getReceiver().getFullName());
        userPaymentDto.setSenderFullName(userPayment.getSender().getFullName());
        userPaymentDto.setReceiverEosFinId(userPayment.getReceiver().getEosFinId());
        userPaymentDto.setSenderEosFinId(userPayment.getSender().getEosFinId());
    }

    public UserPayment confirmPayment(ConfirmPaymentDto confirmPaymentDto) {
        Optional<UserPayment> userPaymentOptional = userPaymentHelper.findById(confirmPaymentDto.getId());
        if (!userPaymentOptional.isPresent()) return null;
        UserPayment userPayment = userPaymentOptional.get();
        userPayment.setConfirmedBy(confirmPaymentDto.getUserId().toString());
        userPayment.setConfirmationDate(new Timestamp(System.currentTimeMillis()));
        userPayment.setConfirmationStatus(DfcConstants.CONFIRMATION_STATUS.CONFIRMED.name());
        return userPaymentHelper.save(userPayment);
    }

    public String getFileName(Integer id) {
        Optional<UserPayment> userPaymentOptional = userPaymentHelper.findById(id);
        if (!userPaymentOptional.isPresent()) return null;
        return userPaymentOptional.get().getScreenshotPath();
    }


    public UserPayment processPaymentScreenshotDetails(Integer id, String path) {
        Optional<UserPayment> userPaymentOptional = userPaymentHelper.findById(id);
        if (!userPaymentOptional.isPresent()) return null;
        UserPayment userPayment = userPaymentOptional.get();
        userPayment.setPaidDate(new Timestamp(System.currentTimeMillis()));
        userPayment.setScreenshotPath(path);
        userPayment.setPaidStatus(DfcConstants.PAID_STATUS.PAID.name());
        return userPaymentHelper.save(userPayment);
    }
}
