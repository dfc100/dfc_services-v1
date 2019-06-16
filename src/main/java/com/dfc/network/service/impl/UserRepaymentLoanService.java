package com.dfc.network.service.impl;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.UserLoanDto;
import com.dfc.network.dto.UserLoanEligibilityDto;
import com.dfc.network.dto.UserLoanRepaymentDto;
import com.dfc.network.helper.StarHelper;
import com.dfc.network.helper.UserHelper;
import com.dfc.network.helper.UserLoanHelper;
import com.dfc.network.helper.UserRepaymentLoanHelper;
import com.dfc.network.model.Star;
import com.dfc.network.model.User;
import com.dfc.network.model.UserLoan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserRepaymentLoanService {

    @Autowired
    private UserRepaymentLoanHelper userRepaymentLoanHelper;

    @Autowired
    private UserLoanHelper userLoanHelper;

    public List<UserLoanRepaymentDto> getLoanSchedule(Integer userId) {
        List<UserLoan> userLoans = userLoanHelper.findByUserUserId(userId);
        List<UserLoanRepaymentDto> userLoanRepaymentDtos = new ArrayList<>();
        Optional.ofNullable(userLoans).ifPresent(userLoansList -> userLoansList.forEach(userLoan -> {
            if (DfcConstants.USER_LOAN_STATUS.SANCTIONED.name().equalsIgnoreCase(userLoan.getLoanStatus())) {
                userLoanRepaymentDtos.addAll(userRepaymentLoanHelper.getLoanRepaymentSchedule(userLoan));
            }
        }));
        return userLoanRepaymentDtos;
    }



}
