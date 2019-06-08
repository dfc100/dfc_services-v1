package com.dfc.network.repository;

import com.dfc.network.model.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Integer> {

    List<UserPayment> findBySenderUserId(Integer senderUserId);

    List<UserPayment> findByReceiverUserId(Integer senderUserId);
}
