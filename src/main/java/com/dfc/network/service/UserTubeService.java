package com.dfc.network.service;

import com.dfc.network.event.PaymentConfirmationEventPublisher;
import com.dfc.network.helper.UserTubeHelper;
import com.dfc.network.model.User;
import com.dfc.network.model.UserTube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserTubeService {

    @Autowired
    private UserTubeHelper userTubeHelper;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PaymentConfirmationEventPublisher paymentConfirmationEventPublisher;

    public UserTube createUserTubeRecord(User user) {
        return userTubeHelper.createUserTubeRecord(user);
    }

}
