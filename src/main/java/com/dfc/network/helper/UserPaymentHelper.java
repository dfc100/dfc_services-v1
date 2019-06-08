package com.dfc.network.helper;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.UserDto;
import com.dfc.network.model.PaymentMaster;
import com.dfc.network.model.User;
import com.dfc.network.model.UserPayment;
import com.dfc.network.model.UserSunflowerInfo;
import com.dfc.network.repository.PaymentMasterRepository;
import com.dfc.network.repository.UserPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserPaymentHelper {

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    @Autowired
    private PaymentMasterRepository paymentMasterRepository;

    @Autowired
    private UserHelper userHelper;

    public void createPaymentRecords(UserDto userDto, User user) {
        UserSunflowerInfo userSunFlowerInfo = new UserSunflowerInfo();
        userSunFlowerInfo.setReferralUserId(userDto.getReferralId());
        userSunFlowerInfo.setUser(user);
        List<PaymentMaster> paymentMasters = paymentMasterRepository.findAll();
        Map<String, Long> paymentMasterMap = convertPaymentMastersToMap(paymentMasters);
        savePaymentDetails(user, user, 1, paymentMasterMap);
        saveAdminPaymentDetail(user, paymentMasterMap);
    }

    private void saveAdminPaymentDetail(User user, Map<String, Long> paymentMasterMap) {
        Optional<User> optionalUser = userHelper.findById(1);
        UserPayment userPayment = new UserPayment();
        userPayment.setSender(user);
        userPayment.setReceiver(optionalUser.get());
        userPayment.setLevel(DfcConstants.ADMIN);
        userPayment.setValue(paymentMasterMap.get(DfcConstants.ADMIN));
        userPayment.setPaidStatus(DfcConstants.PAID_STATUS.NOT_PAID.name());
        userPaymentRepository.save(userPayment);
    }

    private Map<String, Long> convertPaymentMastersToMap(List<PaymentMaster> paymentMasters) {
        Map<String, Long> paymentMasterMap = new HashMap<>();
        Optional.ofNullable(paymentMasters).ifPresent(paymentMasterRecords -> paymentMasterRecords.forEach(paymentMaster -> {
            paymentMasterMap.put(paymentMaster.getLevel(), paymentMaster.getValue());
        }));
        return paymentMasterMap;
    }

    private void savePaymentDetails(User user, User recursiveUser, int start, Map<String, Long> paymentMasterMap) {
        if (start <= DfcConstants.TOTAL_LEVEL) {
            Optional<User> parentUserOptional;
            parentUserOptional = userHelper.findById(recursiveUser.getReferralUserId());
            if (!parentUserOptional.isPresent()) return;
            User parentUser = parentUserOptional.get();
            Long paymentValue = paymentMasterMap.get(String.valueOf(start));
            if (null != paymentValue) {
                UserPayment userPayment = new UserPayment();
                userPayment.setSender(user);
                userPayment.setReceiver(parentUser);
                userPayment.setLevel(String.valueOf(start));
                userPayment.setValue(paymentValue);
                userPayment.setPaidStatus(DfcConstants.PAID_STATUS.NOT_PAID.name());
                userPaymentRepository.save(userPayment);
            }
            start++;
            savePaymentDetails(user, parentUser, start, paymentMasterMap);
        }
    }

    public  List<UserPayment> findBySenderUserId(Integer userId) {
        return userPaymentRepository.findBySenderUserId(userId);
    }

    public  List<UserPayment> findByReceiverUserId(Integer userId) {
        return userPaymentRepository.findByReceiverUserId(userId);
    }


    public  Optional<UserPayment> findById(Integer id) {
        return userPaymentRepository.findById(id);
    }

    public UserPayment save(UserPayment userPayment) {
        return userPaymentRepository.save(userPayment);
    }
}
