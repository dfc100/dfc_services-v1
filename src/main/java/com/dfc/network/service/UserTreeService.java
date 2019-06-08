package com.dfc.network.service;

import com.dfc.network.dto.BinaryInfoDto;
import com.dfc.network.dto.Child;
import com.dfc.network.dto.SunFlowerInfoDto;
import com.dfc.network.helper.UserBinaryInfoHelper;
import com.dfc.network.helper.UserHelper;
import com.dfc.network.helper.UserSunFlowerInfoHelper;
import com.dfc.network.model.User;
import com.dfc.network.model.UserBinaryInfo;
import com.dfc.network.model.UserSunflowerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service("userTreeService")
public class UserTreeService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserSunFlowerInfoHelper userSunFlowerInfoHelper;

    @Autowired
    private UserBinaryInfoHelper userBinaryInfoHelper;

    public SunFlowerInfoDto getSunflowerDetails(Integer userId, int level) {
        Optional<User> userOptional = userHelper.findById(userId);
        if (!userOptional.isPresent()) return null;
        SunFlowerInfoDto sunFlowerInfoDto = null;
        User user = userOptional.get();
        if (userOptional.get() != null) {
            sunFlowerInfoDto= new SunFlowerInfoDto();
            sunFlowerInfoDto.setName(user.getUserName());
            sunFlowerInfoDto.setUserId(userId);
            sunFlowerInfoDto.setCity(user.getCity());
            sunFlowerInfoDto.setState(user.getState());
            sunFlowerInfoDto.setFullName(user.getFullName());

            List<UserSunflowerInfo> userSunflowerInfo = userSunFlowerInfoHelper.findSunflowerInfo(userId);
            if (!CollectionUtils.isEmpty(userSunflowerInfo)) {
                List<Child> children = new ArrayList<>();
                userSunflowerInfo.forEach(userSunflowerInfoDB -> {
                    Child childInside = userSunFlowerInfoHelper.createChild(userSunflowerInfoDB);
                    userSunFlowerInfoHelper.getSunFlowerRecursive(userSunflowerInfoDB.getUser().getUserId(), childInside, level);
                    children.add(childInside);
                });
                if (!CollectionUtils.isEmpty(children)) {
                    sunFlowerInfoDto.setChildren(children);
                }
            }
        }
        return sunFlowerInfoDto;
    }


    public BinaryInfoDto getBinaryDetails(Integer userId, int level) {
        Optional<User> userOptional = userHelper.findById(userId);
        if (!userOptional.isPresent()) return null;
        List<UserBinaryInfo> binaryInfos = userBinaryInfoHelper.findBinaryInfo(userId);
        BinaryInfoDto binaryInfoDto = new BinaryInfoDto();
        User user = userOptional.get();
        binaryInfoDto.setName(user.getUserName());
        binaryInfoDto.setUserId(userId);
        binaryInfoDto.setCity(user.getCity());
        binaryInfoDto.setState(user.getState());
        binaryInfoDto.setFullName(user.getFullName());
        List<Child> children = null;
        if (!CollectionUtils.isEmpty(binaryInfos)) {
            children = new ArrayList<>();
            for (UserBinaryInfo userBinaryInfo : binaryInfos) {
                Child childInside = userBinaryInfoHelper.createChild(userBinaryInfo);
                userBinaryInfoHelper.getBinaryRecursive(userBinaryInfo.getUser().getUserId(), childInside, level);
                children.add(childInside);
            }

        }
        binaryInfoDto.setChildren(children);
        userBinaryInfoHelper.setNumberOfChildren(binaryInfoDto);
        return binaryInfoDto;
    }
}
