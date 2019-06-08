package com.dfc.network.helper;

import com.dfc.network.dto.Child;
import com.dfc.network.dto.UserDto;
import com.dfc.network.model.User;
import com.dfc.network.model.UserSunflowerInfo;
import com.dfc.network.repository.UserSunflowerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSunFlowerInfoHelper {

    @Autowired
    private UserSunflowerInfoRepository userSunflowerInfoRepository;

    public void createUserSunflowerInfo(UserDto userDto, User user) {
        UserSunflowerInfo userSunFlowerInfo = new UserSunflowerInfo();
        userSunFlowerInfo.setReferralUserId(userDto.getReferralId());
        userSunFlowerInfo.setUser(user);
        userSunflowerInfoRepository.save(userSunFlowerInfo);
    }

    public void save(UserSunflowerInfo userSunFlowerInfo) {

        userSunflowerInfoRepository.save(userSunFlowerInfo);
    }


    public List<UserSunflowerInfo> findSunflowerInfo(Integer userId) {
        return userSunflowerInfoRepository.findByReferralUserId(userId);
    }

    public Child createChild(UserSunflowerInfo userSunflowerInfo){
        Child childInside = new Child();
        User user = userSunflowerInfo.getUser();
        childInside.setName(user.getUserName());
        childInside.setUserId(user.getUserId());
        childInside.setCity(user.getCity());
        childInside.setState(user.getState());
        childInside.setFullName(user.getFullName());

        return childInside;
    }

    public void getSunFlowerRecursive(Integer userId, Child child, int level) {
        if (level != 1 ) {
            List<UserSunflowerInfo> userSunflowerInfo = findSunflowerInfo(userId);
            if (!CollectionUtils.isEmpty(userSunflowerInfo)) {
                for (UserSunflowerInfo sunflowerInfo : userSunflowerInfo) {
                    Child childInside = createChild(sunflowerInfo);
                    if (child != null) {
                        if (CollectionUtils.isEmpty(child.getChildren())) {
                            List<Child> childrenInside = new ArrayList<>();
                            childrenInside.add(childInside);
                            child.setChildren(childrenInside);
                        } else {
                            child.getChildren().add(childInside);
                        }
                    }
                    getSunFlowerRecursive(sunflowerInfo.getUser().getUserId(), childInside, level - 1);
                }
            }
        }
    }
}
