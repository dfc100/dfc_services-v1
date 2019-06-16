package com.dfc.network.helper;

import com.dfc.network.model.UserLoan;
import com.dfc.network.repository.UserLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanAdminHelper {

    @Autowired
    private UserLoanRepository userLoanRepository;

    @Autowired
    private UserHelper userHelper;

    public List<UserLoan> getAdminLoans(String loanStatus) {
        return userLoanRepository.findAllByLoanStatus(loanStatus);
    }


    public List<UserLoan> getLoansByUserId(Integer userId) {
        return userLoanRepository.findAllByUserUserId(userId);
    }

}
