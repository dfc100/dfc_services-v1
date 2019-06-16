package com.dfc.network.helper;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.UserLoanRepaymentDto;
import com.dfc.network.model.Star;
import com.dfc.network.model.UserLoan;
import com.dfc.network.model.UserLoanRepayment;
import com.dfc.network.repository.UserRepaymentLoanRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class UserRepaymentLoanHelper {

    @Autowired
    private UserRepaymentLoanRepository userRepaymentLoanRepository;

    @Autowired
    private StarHelper starHelper;

    public List<UserLoanRepaymentDto> getLoanRepaymentSchedule(UserLoan userLoan) {
        List<UserLoanRepayment> userLoanRepayments = userRepaymentLoanRepository.findByUserLoanUserLoanId(userLoan.getUserLoanId());
        List<UserLoanRepaymentDto> userLoanRepaymentDtos = new ArrayList<>();
        Optional.ofNullable(userLoanRepayments).ifPresent(userLoanRepaymentsList -> userLoanRepaymentsList.forEach(userLoanRepayment -> {
            UserLoanRepaymentDto userLoanRepaymentDto = new UserLoanRepaymentDto();
            BeanUtils.copyProperties(userLoanRepayment, userLoanRepaymentDto);
            userLoanRepaymentDtos.add(userLoanRepaymentDto);

        }));
        Star star = starHelper.findByStarId(userLoan.getStarId());
        List<UserLoanRepayment> userLoanRepaymentsNotInDB = calculateRepaymentSchedule(userLoan, star, star.getEwiNoOfWeeks() - userLoanRepayments.size(), userLoanRepaymentDtos.get(userLoanRepaymentDtos.size()-1).getEwiDueDate().getTime());
        Optional.ofNullable(userLoanRepaymentsNotInDB).ifPresent(userLoanRepaymentsNotInDBList -> userLoanRepaymentsNotInDBList.forEach(userLoanRepayment -> {
            UserLoanRepaymentDto userLoanRepaymentDto = new UserLoanRepaymentDto();
            BeanUtils.copyProperties(userLoanRepayment, userLoanRepaymentDto);
            userLoanRepaymentDtos.add(userLoanRepaymentDto);

        }));
        return userLoanRepaymentDtos;
    }

    public void saveLoanRepaymentSchedule(UserLoan userLoan) {
        Star star = starHelper.findByStarId(userLoan.getStarId());
        List<UserLoanRepayment> userLoanRepayments = calculateRepaymentSchedule(userLoan, star, star.getEwiNoOfWeeks(), userLoan.getLoanConfirmedDate().getTime());
        userRepaymentLoanRepository.save(userLoanRepayments.get(0));
    }

    private List<UserLoanRepayment> calculateRepaymentSchedule(UserLoan userLoan, Star star, Integer numberOfWeeks, long lastRecordTime) {
        int firstLoanInstallment = 7;
        //long loanSanctionedTime = userLoan.getLoanConfirmedDate().getTime();
        List<UserLoanRepayment> userLoanRepaymentList = new ArrayList<>();
        for (int i = 1; i <= numberOfWeeks; i++) {
            UserLoanRepayment userLoanRepayment = new UserLoanRepayment();
            userLoanRepayment.setUserLoan(userLoan);
            userLoanRepayment.setEwiAmount(star.getEwiAmount());
            userLoanRepayment.setEwiDueDate(new Timestamp(lastRecordTime + dayToMiliseconds(firstLoanInstallment)));
            userLoanRepayment.setEwiStatus(DfcConstants.USER_LOAN_REPAYMENT_STATUS.NOTPAID.name());
            lastRecordTime = userLoanRepayment.getEwiDueDate().getTime();
            userLoanRepaymentList.add(userLoanRepayment);
        }
        return userLoanRepaymentList;
    }

    private Long dayToMiliseconds(int days){
        Long result = Long.valueOf(days * 24 * 60 * 60 * 1000);
        return result;
    }

}
