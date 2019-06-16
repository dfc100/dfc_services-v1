package com.dfc.network.service;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.UserLoanDto;
import com.dfc.network.helper.LoanAdminHelper;
import com.dfc.network.helper.UserLoanHelper;
import com.dfc.network.helper.UserRepaymentLoanHelper;
import com.dfc.network.model.User;
import com.dfc.network.model.UserLoan;
import com.dfc.network.model.UserPayment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class LoanAdminService {

    @Autowired
    private LoanAdminHelper loanAdminHelper;

    @Autowired
    private UserLoanHelper userLoanHelper;

    @Autowired
    private UserRepaymentLoanHelper userRepaymentLoanHelper;

    public List<UserLoanDto> getAdminLoans(String loanStatus) {
        List<UserLoan> userLoans =  loanAdminHelper.getAdminLoans(loanStatus);
        List<UserLoanDto> userLoanDtos = new ArrayList<>();
        Optional.ofNullable(userLoans).ifPresent(userLoanList -> userLoanList.forEach(userLoan ->  {
            UserLoanDto userLoanDto = new UserLoanDto();
            BeanUtils.copyProperties(userLoan, userLoanDto);
            User user = userLoan.getUser();
            userLoanDto.setUserId(user.getUserId());
            userLoanDto.setUserName(user.getUserName());
            userLoanDto.setFullName(user.getFullName());
            userLoanDto.setEosFinId(user.getEosFinId());
            userLoanDto.setCurrency(userLoan.getLoanCurrency());
            userLoanDtos.add(userLoanDto);
        }));
        return userLoanDtos;
    }

    public List<UserLoanDto> getLoansByUserId(Integer userId) {
        List<UserLoan> userLoans =  loanAdminHelper.getLoansByUserId(userId);
        List<UserLoanDto> userLoanDtos = new ArrayList<>();
        Optional.ofNullable(userLoans).ifPresent(userLoanList -> userLoanList.forEach(userLoan ->  {
            UserLoanDto userLoanDto = new UserLoanDto();
            BeanUtils.copyProperties(userLoan, userLoanDto);
            userLoanDtos.add(userLoanDto);
        }));
        return userLoanDtos;
    }

    public UserLoanDto confirmLoan(UserLoanDto userLoanDto) {
        UserLoan userLoan = userLoanHelper.findById(userLoanDto.getUserLoanId());
        if (userLoan == null) return null;
        userLoan.setLoanConfirmedDate(new Timestamp(System.currentTimeMillis()));
        userLoan.setLoanStatus(DfcConstants.USER_LOAN_STATUS.SANCTIONED.name());
        userLoan.setTransactionDate(userLoanDto.getTransactionDate());
        userLoan.setTransactionId(userLoanDto.getTransactionId());
        userRepaymentLoanHelper.saveLoanRepaymentSchedule(userLoan);
        BeanUtils.copyProperties(userLoan, userLoanDto);
        return userLoanDto;
    }

}
