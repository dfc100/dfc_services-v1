package com.dfc.network.repository;

import com.dfc.network.model.UserBinaryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBinaryInfoRepository extends JpaRepository<UserBinaryInfo, Integer> {
    UserBinaryInfo findByParentIdAndPosition(Integer parentId, String position);
    List<UserBinaryInfo> findByParentId(Integer parentId);
    UserBinaryInfo findByUserUserId(Integer userId);

}
