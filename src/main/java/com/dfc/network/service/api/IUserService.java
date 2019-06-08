package com.dfc.network.service.api;

import com.dfc.network.dto.UserDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.model.User;
import com.dfc.network.model.VerificationToken;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto)
            throws CustomMessageException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    VerificationToken createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}
