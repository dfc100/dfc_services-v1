package com.dfc.network.helper;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.UserDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.model.User;
import com.dfc.network.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class UserHelper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkAlreadyExists(UserDto userDto) {
        return userDto.getUserName() != null ? userRepository.findByUserName(userDto.getUserName()) != null :
                userDto.getEmail() != null ? userRepository.findByEmail(userDto.getEmail()) != null :
                        userDto.getMobileNo() != null ? userRepository.findByMobileNo(userDto.getMobileNo()) != null : false;
    }

    public Optional<User> findById(Integer id) {
        return Optional.of(userRepository.findByUserId(id));
    }


    public List<User> findByReferralId(Integer id) {
        return userRepository.findByReferralUserId(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public User saveUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setStatus(userDto.getStatus());
        user.setReferralUserId(userDto.getReferralId());
        user.setReferralBinaryPosition(userDto.getPosition());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public User loginUser(String loginName) {
        return userRepository.findByMobileNoOrUserNameOrEmail(loginName, loginName, loginName);
    }

    public boolean validatePassword(User user, String loginPassword) throws CustomMessageException {
        boolean isPasswordMatches = false;
        if (user != null) {
            if (!new BCryptPasswordEncoder(11).matches(loginPassword, user.getPassword())) {
                throw new CustomMessageException(HttpStatus.UNAUTHORIZED, "Invalid password");
            }
            isPasswordMatches = true;
        }
        return isPasswordMatches;
    }


    public void buildUserDto(UserDto userDto, User user) {
        BeanUtils.copyProperties(user, userDto, DfcConstants.IGNORE_PROPERTIES);
    }

    public User updateUser(UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userDto.getUserId());
        if (!userOptional.isPresent()) return null;
        User user = userOptional.get();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(userDto, user);
        user.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

}
/*
    public User findUserByCgnRecordId(UserDto userDto) {
        return userRepository.findByCgnRecordCgnRecordIdAndRoleRoleId(userDto.getId(), userDto.getRoleId());
    }

    public User findUserByEmailAndRoleId(UserDto userDto) {
        return userRepository.findByEmailAndRoleRoleId(userDto.getEmail(), userDto.getRoleId());
    }

    public User findUserByCgnRecordId(Integer cgnId, Integer roleId) {
        return userRepository.findByCgnRecordCgnRecordIdAndRoleRoleId(cgnId, roleId);
    }

    public UserProjection findUserByCgnRecordIdProjection(Integer cgnId, Integer roleId) {
        return userRepository.findByCgnRecordCgnRecordIdAndRoleRoleId(cgnId, roleId, UserProjection.class);
    }

    public User updateUser(UserDto userDto) {
        User user = findUserByCgnRecordId(userDto);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(userDto, user);
        user.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }





    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    }*/
