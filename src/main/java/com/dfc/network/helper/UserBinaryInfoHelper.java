package com.dfc.network.helper;

import com.dfc.network.dto.BinaryInfoDto;
import com.dfc.network.dto.Child;
import com.dfc.network.dto.UserDto;
import com.dfc.network.model.User;
import com.dfc.network.model.UserBinaryInfo;
import com.dfc.network.repository.UserBinaryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBinaryInfoHelper {

    @Autowired
    private UserBinaryInfoRepository userBinaryInfoRepository;


    public void createUserBinaryInfo(UserDto userDto, User user) {
        if (userBinaryInfoRepository.findByUserUserId(user.getUserId()) == null) {
            UserBinaryInfo userBinaryInfo = new UserBinaryInfo();
            userBinaryInfo.setPosition(userDto.getPosition());
            userBinaryInfo.setUser(user);
            if ("D".equalsIgnoreCase(userDto.getPosition())) {
                userBinaryInfo.setParentId(findLastUpdated(userDto));
                userBinaryInfo.setPosition(userDto.getPosition());
            } else {
                userBinaryInfo.setParentId(getParentId(userDto.getReferralId(), userDto.getPosition()));
            }
            userBinaryInfoRepository.save(userBinaryInfo);
        }

    }


    private Integer findLastUpdated(UserDto userDto){
        int referralId = userDto.getReferralId();
        Integer parentIdLeft;
        Integer parentIdRight;
        UserBinaryInfo userBinaryInfoOptionalLeft = null;
        UserBinaryInfo userBinaryInfoOptionalRight = null;
        parentIdLeft = getParentId(referralId, "L");
        if (parentIdLeft != null) {
            userBinaryInfoOptionalLeft = userBinaryInfoRepository.findByUserUserId(parentIdLeft);
        }
        parentIdRight = getParentId(referralId, "R");
        if (parentIdRight != null) {
            userBinaryInfoOptionalRight = userBinaryInfoRepository.findByUserUserId(parentIdRight);
        }
        if (userBinaryInfoOptionalLeft == null && userBinaryInfoOptionalRight == null) {
            userDto.setPosition("L");
            return parentIdLeft;
        } else if (userBinaryInfoOptionalLeft == null && userBinaryInfoOptionalRight != null) {
            userDto.setPosition("L");
            return parentIdLeft;
        } else if (userBinaryInfoOptionalLeft != null && userBinaryInfoOptionalRight == null) {
            userDto.setPosition("R");
            return parentIdRight;
        } else {
            if (userBinaryInfoOptionalLeft.getUserBinaryInfoId() > userBinaryInfoOptionalRight.getUserBinaryInfoId()) {
                userDto.setPosition("R");
                return parentIdRight;
            } else {
                userDto.setPosition("L");
                return parentIdLeft;
            }
        }

    }

    private Integer getParentId(int parentId, String position) {
        UserBinaryInfo userBinaryInfo = userBinaryInfoRepository.findByParentIdAndPosition(parentId, position);
        return userBinaryInfo != null ? getParentId(userBinaryInfo.getUser().getUserId(), position) : parentId;
    }

    public void save(UserBinaryInfo userBinaryInfo) {
        userBinaryInfoRepository.save(userBinaryInfo);
    }

    public List<UserBinaryInfo> findBinaryInfo(Integer userId) {
        return userBinaryInfoRepository.findByParentId(userId);
    }

    public Child createChild(UserBinaryInfo userBinaryInfo){
        Child childInside = new Child();
        User user = userBinaryInfo.getUser();
        childInside.setName(userBinaryInfo.getUser().getUserName());
        childInside.setUserId(userBinaryInfo.getUser().getUserId());
        childInside.setPosition(userBinaryInfo.getPosition());
        childInside.setCity(user.getCity());
        childInside.setState(user.getState());
        childInside.setFullName(user.getFullName());

        return childInside;
    }

    public void setNumberOfChildren(BinaryInfoDto binaryInfoDto) {
        List<Child> children =  binaryInfoDto.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            binaryInfoDto.setNumberOfChildren(children.size());
            children.forEach(child -> {
                setNumberOfChildrenRecursive(child);
            });
        }
    }

    private void setNumberOfChildrenRecursive(Child child) {
        List<Child> children =  child.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            child.setNumberOfChildren(children.size());
            children.forEach(childInside ->{
                setNumberOfChildrenRecursive(childInside);
            });
        } else  {
            List<UserBinaryInfo> userBinaryInfos = userBinaryInfoRepository.findByParentId(child.getUserId());
            if (!CollectionUtils.isEmpty(userBinaryInfos)) {
                child.setNumberOfChildren(userBinaryInfos.size());
            }
        }
    }

    public void getBinaryRecursive(Integer userId, Child child, int level) {
        if (level != 1 ) {
            List<UserBinaryInfo> binaryInfos = findBinaryInfo(userId);
            if (!CollectionUtils.isEmpty(binaryInfos)) {
                for (UserBinaryInfo userBinaryInfo : binaryInfos) {
                    Child childInside = createChild(userBinaryInfo);
                    if (child != null) {
                        if (CollectionUtils.isEmpty(child.getChildren())) {
                            List<Child> childrenInside = new ArrayList<>();
                            childrenInside.add(childInside);
                            child.setChildren(childrenInside);
                        } else {
                            child.getChildren().add(childInside);
                        }
                    }
                    getBinaryRecursive(userBinaryInfo.getUser().getUserId(), childInside, level - 1);
                }
            }
        }
    }


}
