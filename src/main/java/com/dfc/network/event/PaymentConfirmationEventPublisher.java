package com.dfc.network.event;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.helper.UserHelper;
import com.dfc.network.helper.UserPaymentHelper;
import com.dfc.network.model.User;
import com.dfc.network.model.UserPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PaymentConfirmationEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserPaymentHelper userPaymentHelper;

    public void publishEvent(final UserPayment userPayment) {
        Integer userId = userPayment.getSenderId();
        User user = getUser(userId);

        if (user != null && shouldPublish(userId)) {
            UserEvent userEvent = new UserEvent(this, user);
            applicationEventPublisher.publishEvent(userEvent);
        }
    }


    private boolean shouldPublish(Integer userId){
        List<UserPayment> userPayments =  userPaymentHelper.findBySenderUserId(userId);
        List<UserPayment> unConfirmedPayments = userPayments.stream().filter(userPayment -> !DfcConstants.PAYMENT_STATUS.CONFIRMED.name().equalsIgnoreCase(userPayment.getPaidStatus()))
                .collect(Collectors.toList());
        return unConfirmedPayments == null || CollectionUtils.isEmpty(unConfirmedPayments);
    }


    private User getUser(Integer userId){
        Optional<User> parentUserOptional = userHelper.findById(userId);
        if (!parentUserOptional.isPresent()) return null;
        return parentUserOptional.get();
    }
}
