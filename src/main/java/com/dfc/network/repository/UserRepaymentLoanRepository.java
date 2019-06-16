package com.dfc.network.repository;

import com.dfc.network.model.UserLoanRepayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepaymentLoanRepository extends JpaRepository<UserLoanRepayment, Integer> {
    List<UserLoanRepayment> findByUserLoanUserLoanId(Integer userLoanId);
}
