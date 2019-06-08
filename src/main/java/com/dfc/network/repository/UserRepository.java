package com.dfc.network.repository;

import com.dfc.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByUserName(String userName);
    User findByMobileNo(String mobileNo);
    User findByMobileNoOrUserNameOrEmail(String mobileNo, String userName, String email);
}
