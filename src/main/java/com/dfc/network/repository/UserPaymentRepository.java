package com.dfc.network.repository;

import com.dfc.network.model.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Integer> {

    List<UserPayment> findBySenderId(Integer senderUserId);

    List<UserPayment> findByReceiverId(Integer receiverUserId);

    List<UserPayment> findByReceiverIdAndPaidStatus(Integer receiverUserId, String paidStatus);

    UserPayment findByUserPaymentId(Integer id);
}
