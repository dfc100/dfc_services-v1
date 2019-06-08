package com.dfc.network.service.impl;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.UserDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.helper.UserBinaryInfoHelper;
import com.dfc.network.helper.UserHelper;
import com.dfc.network.helper.UserPaymentHelper;
import com.dfc.network.helper.UserSunFlowerInfoHelper;
import com.dfc.network.model.User;
import com.dfc.network.model.UserBinaryInfo;
import com.dfc.network.model.UserSunflowerInfo;
import com.dfc.network.model.VerificationToken;
import com.dfc.network.service.api.IUserService;
import com.dfc.network.validator.UserRegistrationValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Optional;

@Transactional
@Service("userDetailsService")
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserBinaryInfoHelper userBinaryInfoHelper;

    @Autowired
    private UserSunFlowerInfoHelper userSunFlowerInfoHelper;

    @Autowired
    private UserPaymentHelper userPaymentHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

/*


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserSunflowerInfoRepository userSunflowerInfoRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private UserCreateEventPublisher userCreateEventPublisher;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BankInfoHelper bankInfoHelper;

    @Autowired
    private KycDetailHelper kycDetailHelper;

    @Autowired
    private VerificationTokenHelper verificationTokenHelper;

    @Autowired
    private PasswordResetTokenHelper passwordResetTokenHelper;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;*/


    @Override
    @Transactional
    public User registerNewUserAccount(UserDto userDto)
            throws CustomMessageException {
        userRegistrationValidator.validate(userDto);
        userDto.setStatus(UserDto.UserStatus.INITATED);
        User user = userHelper.saveUser(userDto);
        userSunFlowerInfoHelper.createUserSunflowerInfo(userDto, user);
        userBinaryInfoHelper.createUserBinaryInfo(userDto, user);
        userPaymentHelper.createPaymentRecords(userDto, user);
        return user;
    }

    public UserDto getUserInfo(Integer userId) {

        Optional<User> userOptional = userHelper.findById(userId);
        UserDto userDto =null;
        if (userOptional.isPresent()) {
            userDto = new UserDto();
            BeanUtils.copyProperties(userOptional.get(), userDto, DfcConstants.IGNORE_PROPERTIES);
        }
        return userDto;
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) {
        User user = userHelper.updateUser(userDto);
        UserDto userDtoResponse = null;
        if (user != null) {
            userDtoResponse = new UserDto();
            userHelper.buildUserDto(userDtoResponse, user);
        }
        return userDtoResponse;
    }

    @Override
    public User getUser(String verificationToken) {
        return null;
    }

    @Override
    public void saveRegisteredUser(User user) {

    }

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        return null;
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return null;
    }

    @Transactional
    public User changePassword(UserDto userDto) throws CustomMessageException {
        Optional<User> userOptional = userHelper.findById(userDto.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userHelper.validatePassword(user, userDto.getOldPassword())) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
                return userHelper.saveUser(user);
            } else {
                throw new CustomMessageException(HttpStatus.UNAUTHORIZED, "UnAuthorized to change the Password. Contact Admin");
            }
        } else return null;
    }


 /*


    @Transactional
    public VerificationToken verifyEmail(UserDto userDto) {
        User user = userHelper.findUserByCgnRecordId(userDto);
        return verificationTokenHelper.verifyEmail(user, CgnConstants.VERIFICATION_EMAIL, CgnConstants.VERIFICATION_MESSAGE);

    }

    @Transactional
    public PasswordResetToken forgotPassword(UserDto userDto) throws CustomMessageException{
        User user = userHelper.findUserByEmailAndRoleId(userDto);
        if (user != null) {
            return passwordResetTokenHelper.sendEmail(user, CgnConstants.FORGOT_PWD_EMAIL, CgnConstants.FORGOT_PWD_MESSAGE);
        }
        return null;
    }


    }*/

    @Transactional
    public User registerAdminNewUserAccount(UserDto userDto) {
        userDto.setReferralId(0);
        userDto.setStatus(UserDto.UserStatus.ACTIVE);
        User user = userHelper.saveUser(userDto);
        UserSunflowerInfo userSunFlowerInfo = new UserSunflowerInfo();
        userSunFlowerInfo.setReferralUserId(0);
        userSunFlowerInfo.setUser(user);
        userSunFlowerInfoHelper.save(userSunFlowerInfo);
        UserBinaryInfo userBinaryInfo = new UserBinaryInfo();
        userBinaryInfo.setPosition(userDto.getPosition());
        userBinaryInfo.setUser(user);
        userBinaryInfo.setParentId(0);
        userBinaryInfoHelper.save(userBinaryInfo);
        return user;
    }


    @Transactional
    public User registerSuperAdminNewUserAccount(UserDto userDto) {
        userDto.setStatus(UserDto.UserStatus.ACTIVE);
        User user = userHelper.saveUser(userDto);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public boolean checkAlreadyExists(UserDto userDto) {
        return userHelper.checkAlreadyExists((userDto));
    }

    public UserDto loginUser(UserDto userDto) throws CustomMessageException{
        User user = userHelper.loginUser(userDto.getUserName());
        UserDto userDtoResponse = null;
        if (userHelper.validatePassword(user, userDto.getPassword())) {
            userDtoResponse = new UserDto();
            userHelper.buildUserDto(userDtoResponse, user);
        }
        return userDtoResponse;
    }

   /*
    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? true :false;
    }

    @Override
    public User getUser(String verificationToken) {
        return tokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken verificationToken;
        verificationToken = tokenRepository.findByUser(user);
        verificationToken = verificationToken != null ? verificationToken : new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        Instant now = Instant.now(); //current date
        Instant after= now.plus(Duration.ofDays(CgnConstants.EMAIL_VERIFICATION_VALIDITY_IN_DAYS));
        Date dateAfter = Date.from(after);

        verificationToken.setExpiryDate(dateAfter);
        return tokenRepository.save(verificationToken);
    }




    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return CgnConstants.TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime()
                - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return CgnConstants.TOKEN_EXPIRED;
        }

        user.setEmailVerified(true);
        tokenRepository.delete(verificationToken);
        user.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return CgnConstants.TOKEN_VALID;
    }


    public User resetForgotPassword(UserDto userDto, String token) throws CustomMessageException {
        User user = null;
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        String message =  passwordResetTokenHelper.validatePasswordResetToken(passwordResetToken);
        if (message == null) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, "Unable to reset the password. Please Contact Admin");
        } else if (message.equalsIgnoreCase(CgnConstants.TOKEN_INVALID) || message.equalsIgnoreCase(CgnConstants.TOKEN_EXPIRED)) {
            throw new CustomMessageException(HttpStatus.BAD_REQUEST, message);
        } else if (message.equalsIgnoreCase(CgnConstants.TOKEN_VALID) ){
            user = passwordResetToken.getUser();
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
            user = userRepository.save(user);
        }
        return user;
    }
*/
}