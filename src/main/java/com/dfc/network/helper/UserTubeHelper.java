package com.dfc.network.helper;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.model.User;
import com.dfc.network.model.UserTube;
import com.dfc.network.repository.UserTubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTubeHelper {

    @Autowired
    private UserTubeRepository userTubeRepository;

    @Autowired
    private UserHelper userHelper;

    public UserTube createUserTubeRecord(User user) {
        UserTube userTube = new UserTube();
        userTube.setUser(user);
        userTube.setStarId(user.getStar().getStarId());
        userTube.setStatus(DfcConstants.USER_TUBE_STATUS.ACTIVE.name());
        return userTubeRepository.save(userTube);
    }


}
