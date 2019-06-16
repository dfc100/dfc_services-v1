package com.dfc.network.service;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.dto.UserLoanDto;
import com.dfc.network.dto.UserLoanEligibilityDto;
import com.dfc.network.exception.CustomMessageException;
import com.dfc.network.helper.StarHelper;
import com.dfc.network.helper.UserHelper;
import com.dfc.network.helper.UserLoanHelper;
import com.dfc.network.model.Star;
import com.dfc.network.model.User;
import com.dfc.network.model.UserLoan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserLoanService {

    @Autowired
    private UserLoanHelper userLoanHelper;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private StarHelper starHelper;

    public List<UserLoanEligibilityDto> getLoanEligibility(Integer userId) {
        Optional<User> userOptional = userHelper.findById(userId);
        List<UserLoanEligibilityDto> userLoanEligibilityDtos;
        if (!userOptional.isPresent())  return null;
        userLoanEligibilityDtos = getLoanDetailsForAllStar();
        Star userStar = userOptional.get().getStar();
        if (userStar != null) {
            List<UserLoanDto> userLoanDtos = getLoanDetails(userId);
            if (!CollectionUtils.isEmpty(userLoanDtos)) {
                outer : for (UserLoanDto userLoanDto: userLoanDtos) {
                    inner : for (UserLoanEligibilityDto userLoanEligibilityDto: userLoanEligibilityDtos) {
                        if (userLoanDto.getStarId() == userLoanEligibilityDto.getStarId()) {
                            userLoanEligibilityDto.setLoanEligibilityStatus(userLoanDto.getLoanStatus());
                            break inner;
                        }
                    }
                }
            } else {
                userLoanEligibilityDtos.stream().forEach(userLoanEligibilityDto -> {
                    if ((userLoanEligibilityDto.getStarId()) <= userStar.getStarId()) {
                        userLoanEligibilityDto.setLoanEligibilityStatus(DfcConstants.USER_LOAN_ELIGIBILITY_STATUS.ELIGIBLE.name());
                    }
                });
            }
        }
        return userLoanEligibilityDtos;

    }


    public List<UserLoanDto> getLoanDetails(Integer userId) {
        List<UserLoanDto> userLoanDtos = new ArrayList<>();
        Optional<User> userOptional = userHelper.findById(userId);
        if (!userOptional.isPresent()) return userLoanDtos;
        Optional.ofNullable(userOptional.get().getUserLoans()).ifPresent(userLoans -> userLoans.forEach(userLoan ->  {
            UserLoanDto userLoanDto = new UserLoanDto();
            BeanUtils.copyProperties(userLoan, userLoanDto);
            userLoanDtos.add(userLoanDto);
        }));
        return userLoanDtos;
    }


    public UserLoanDto applyLoan(UserLoanEligibilityDto userLoanEligibilityDto) throws CustomMessageException{
        if (userLoanHelper.findByUserUserIdAndStarId(userLoanEligibilityDto.getUserId(), userLoanEligibilityDto.getStarId()) == null) {
            return userLoanHelper.createLoanRecord(userLoanEligibilityDto);
        } else {
            throw new CustomMessageException(HttpStatus.NOT_ACCEPTABLE, "There is already a Loan record for the given User Id and the star Combination");
        }
    }

    private List<UserLoanEligibilityDto> getLoanDetailsForAllStar(){
        List<Star> stars = starHelper.findAllStars();
        List<UserLoanEligibilityDto> userLoanEligibilityDtos = new ArrayList<>();
        Optional.ofNullable(stars).ifPresent(starsList -> starsList.forEach(star -> {
            UserLoanEligibilityDto userLoanEligibilityDto = new UserLoanEligibilityDto();
            userLoanEligibilityDto.setCurrency(star.getCurrency());
            userLoanEligibilityDto.setEligibilityStar(star.getEligibilityStar());
            userLoanEligibilityDto.setEwiAmount(star.getEwiAmount());
            userLoanEligibilityDto.setEwiNoOfWeeks(star.getEwiNoOfWeeks());
            userLoanEligibilityDto.setLoanAmount(star.getLoanAmount());
            userLoanEligibilityDto.setNoOfLegs(star.getNoOfLegs());
            userLoanEligibilityDto.setPersonalPreference(star.getPersonalPreference());
            userLoanEligibilityDto.setLoanEligibilityStatus(DfcConstants.USER_LOAN_ELIGIBILITY_STATUS.NOT_ELIGIBLE.name());
            userLoanEligibilityDto.setStarId(star.getStarId());
            userLoanEligibilityDto.setStarName(star.getStarName());
            userLoanEligibilityDtos.add(userLoanEligibilityDto);
        }));
        return userLoanEligibilityDtos;

    }

}
