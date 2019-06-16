package com.dfc.network.event;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.UserDto;
import com.dfc.network.helper.StarHelper;
import com.dfc.network.helper.UserBinaryInfoHelper;
import com.dfc.network.helper.UserHelper;
import com.dfc.network.helper.UserTubeHelper;
import com.dfc.network.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener implements ApplicationListener<UserEvent>{

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserBinaryInfoHelper userBinaryInfoHelper;

    @Autowired
    private StarHelper starHelper;

    @Autowired
    private UserTubeHelper userTubeHelper;

    @Override
    public void onApplicationEvent(UserEvent event) {
        doProcess(event);
    }

    private void doProcess(UserEvent event) {
        User user = event.getUser();
        UserDto userDto = new UserDto();
        userDto.setReferralId(user.getReferralUserId());
        userDto.setPosition(user.getReferralBinaryPosition());
        userDto.setUserId(user.getUserId());
        user.setStatus(DfcConstants.USER_STATUS.ACTIVE.name());
        userHelper.saveUser(user);
        userBinaryInfoHelper.createUserBinaryInfo(userDto, user);
        starHelper.processStarEligibleRules(user);
    }
}
