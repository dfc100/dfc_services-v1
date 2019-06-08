package com.dfc.network.repository;

import com.dfc.network.model.UserSunflowerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSunflowerInfoRepository extends JpaRepository<UserSunflowerInfo, Long> {

    List<UserSunflowerInfo> findByReferralUserId(Integer userId);

}
