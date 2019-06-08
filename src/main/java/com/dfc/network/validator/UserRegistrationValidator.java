package com.dfc.network.validator;

import com.dfc.network.dto.UserDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.helper.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationValidator {

    @Autowired
    private UserHelper userHelper;

    public boolean validate(UserDto userDto) throws CustomMessageException {
        if (userHelper.checkAlreadyExists(userDto)) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST,
                    "There is already an account associated with the given email address or MobileNo or UserName: "
                            + userDto.getEmail() + " or " + userDto.getMobileNo() + " or " + userDto.getUserName());
        }

        if (!userHelper.findById(userDto.getReferralId()).isPresent()) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST,
                    "There is no referral exists with the referral id: "
                            + userDto.getReferralId());
        }
        return true;
    }
}
