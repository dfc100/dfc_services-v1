package com.dfc.network.service;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.ConfirmPaymentDto;
import com.dfc.network.dto.UserPaymentDto;
import com.dfc.network.event.PaymentConfirmationEventPublisher;
import com.dfc.network.helper.UserHelper;
import com.dfc.network.helper.UserPaymentHelper;
import com.dfc.network.model.User;
import com.dfc.network.model.UserPayment;
import com.sun.tools.javac.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private UserHelper userHelper;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PaymentConfirmationEventPublisher paymentConfirmationEventPublisher;

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


    public List<UserPaymentDto> getMentorPayments(Integer userId, String paidStatus) {
        List<UserPayment> userPayments = userPaymentHelper.findByReceiverIdAndPaidStatus(userId, StringUtils.toUpperCase(paidStatus));
        List<UserPaymentDto> userPaymentDtos = new ArrayList<>();
        Optional.ofNullable(userPayments).ifPresent(userPaymentList -> userPaymentList.forEach(userPayment -> {
            UserPaymentDto userPaymentDto = new UserPaymentDto();
            setUserPaymentDto(userPayment,userPaymentDto);
            userPaymentDtos.add(userPaymentDto);
        }));

        return userPaymentDtos;
    }


    private void setUserPaymentDto(UserPayment userPayment, UserPaymentDto userPaymentDto) {
        User sender = userHelper.findById(userPayment.getSenderId()).get();
        User receiver = userHelper.findById(userPayment.getReceiverId()).get();
        userPaymentDto.setConfirmationDate(userPayment.getConfirmationDate());
        userPaymentDto.setUserPaymentId(userPayment.getUserPaymentId());
        userPaymentDto.setConfirmedBy(userPayment.getConfirmedBy());
        userPaymentDto.setLevel(userPayment.getLevel());
        userPaymentDto.setPaidDate(userPayment.getPaidDate());
        userPaymentDto.setPaidStatus(userPayment.getPaidStatus());
        userPaymentDto.setReceiverId(userPayment.getReceiverId());
        userPaymentDto.setScreenshotPath(userPayment.getScreenshotPath());
        userPaymentDto.setSenderId(sender.getUserId());
        userPaymentDto.setValue(userPayment.getValue());
        userPaymentDto.setReceiverFullName(receiver.getFullName());
        userPaymentDto.setSenderFullName(sender.getFullName());
        userPaymentDto.setSenderUserName(sender.getUserName());
        userPaymentDto.setReceiverUserName(receiver.getUserName());
        userPaymentDto.setReceiverEosFinId(receiver.getEosFinId());
        userPaymentDto.setSenderEosFinId(sender.getEosFinId());
    }

    public UserPayment confirmPayment(ConfirmPaymentDto confirmPaymentDto) {
        Optional<UserPayment> userPaymentOptional = userPaymentHelper.findById(confirmPaymentDto.getUserPaymentId());
        if (!userPaymentOptional.isPresent()) return null;
        UserPayment userPayment = userPaymentOptional.get();
        userPayment.setConfirmedBy(confirmPaymentDto.getUserId().toString());
        userPayment.setConfirmationDate(new Timestamp(System.currentTimeMillis()));
        userPayment.setPaidStatus(DfcConstants.PAYMENT_STATUS.CONFIRMED.name());
        System.out.println("Service   " + Thread.currentThread().getName());
        paymentConfirmationEventPublisher.publishEvent(userPayment);
        return userPaymentHelper.save(userPayment);
    }

    public String getFileName(Integer id) {
        Optional<UserPayment> userPaymentOptional = userPaymentHelper.findById(id);
        if (!userPaymentOptional.isPresent()) return null;
        return userPaymentOptional.get().getScreenshotPath();
    }


    public UserPayment processPaymentScreenshotDetails(Integer id, String path, String transactionId, String transactionDate) {
        Optional<UserPayment> userPaymentOptional = userPaymentHelper.findById(id);
        if (!userPaymentOptional.isPresent()) return null;
        UserPayment userPayment = userPaymentOptional.get();
        userPayment.setPaidDate(new Timestamp(System.currentTimeMillis()));
        userPayment.setScreenshotPath(path);
        userPayment.setTransactionDate(new Timestamp(Long.valueOf(transactionDate)));
        userPayment.setTransactionId(transactionId);
        userPayment.setPaidStatus(DfcConstants.PAYMENT_STATUS.PAID.name());
        return userPaymentHelper.save(userPayment);
    }
}
