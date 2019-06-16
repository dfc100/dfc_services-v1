package com.dfc.network.repository;

import com.dfc.network.model.UserLoan;
import com.dfc.network.model.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLoanRepository extends JpaRepository<UserLoan, Integer> {
    List<UserLoan> findAllByLoanStatus(String loanStatus);
    UserLoan findByUserLoanId(Integer userLoanId);
    List<UserLoan> findAllByUserUserId(Integer userId);
    UserLoan findByUserUserIdAndStarId(Integer userId, Integer starId);
    List<UserLoan> findByUserUserId(Integer userId);
}
