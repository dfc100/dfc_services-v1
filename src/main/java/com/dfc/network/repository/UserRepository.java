package com.dfc.network.repository;

import com.dfc.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByUserName(String userName);
    User findByMobileNo(String mobileNo);
    User findByMobileNoOrUserNameOrEmail(String mobileNo, String userName, String email);
    List<User> findByReferralUserId(Integer referralUserId);
    User findByUserId(Integer userId);
}
