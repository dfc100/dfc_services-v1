package com.dfc.network.helper;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.UserLoanDto;
import com.dfc.network.dto.UserLoanEligibilityDto;
import com.dfc.network.model.User;
import com.dfc.network.model.UserLoan;
import com.dfc.network.repository.UserLoanRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class UserLoanHelper {

    @Autowired
    private UserLoanRepository userLoanRepository;

    @Autowired
    private UserHelper userHelper;

    public UserLoan findById(Integer loanId) {
        return userLoanRepository.findByUserLoanId(loanId);
    }

    public UserLoan findByUserUserIdAndStarId(Integer userId, Integer starId) {
        return userLoanRepository.findByUserUserIdAndStarId(userId, starId);
    }

    public List<UserLoan> findByUserUserId(Integer userId) {
        return userLoanRepository.findByUserUserId(userId);
    }

    public UserLoan save(UserLoan userLoan) {
        return userLoanRepository.save(userLoan);
    }

    public UserLoanDto createLoanRecord(UserLoanEligibilityDto userLoanEligibilityDto) {
        UserLoan userLoan = new UserLoan();
        Optional<User> userOptional =  userHelper.findById(userLoanEligibilityDto.getUserId());
        if (!userOptional.isPresent()) return null;
        User user = userOptional.get();
        userLoan.setUser(user);
        userLoan.setLoanAmount(userLoanEligibilityDto.getLoanAmount());
        userLoan.setLoanRequestedDate(new Timestamp(System.currentTimeMillis()));
        userLoan.setLoanStatus(DfcConstants.USER_LOAN_STATUS.INITIATED.name());
        userLoan.setStarId(userLoanEligibilityDto.getStarId());
        userLoan.setLoanCurrency(userLoanEligibilityDto.getCurrency());
        UserLoanDto userLoanDto = new UserLoanDto();
        BeanUtils.copyProperties(userLoan,userLoanDto);
        userLoan =  userLoanRepository.save(userLoan);
        BeanUtils.copyProperties(userLoan,userLoanDto);
        userLoanDto.setUserId(user.getUserId());
        return userLoanDto;
    }


}
