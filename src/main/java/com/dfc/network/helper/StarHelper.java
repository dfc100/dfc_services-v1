package com.dfc.network.helper;

import com.dfc.network.constants.DfcConstants;
import com.dfc.network.model.Star;
import com.dfc.network.model.User;
import com.dfc.network.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StarHelper {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private StarRepository starRepository;

    private boolean isParentChanged;

    @Autowired
    private UserTubeHelper userTubeHelper;

    public Star findByStarId(Integer starId) {
        return starRepository.findByStarId(starId);
    }

    public List<Star> findAllStars() {
        return starRepository.findAll();
    }


    private Integer getToBeEligibleStarId(User user) {
        if (user.getStar() != null) {
            return user.getStar().getStarId() + 1;
        }
        else return 1;
    }

    private User saveUser(User user, Integer toBeEligibleStarId) {
        Star star = starRepository.findByStarId(toBeEligibleStarId);
        if (star != null) {
            user.setStar(star);
            return userHelper.saveUser(user);
        }
        return null;
    }

    public void processStarEligibleRules(User user) {
        User parentUser = userHelper.findById(user.getReferralUserId()).get();
        List<User> users = userHelper.findByReferralId(user.getReferralUserId());
        if (!CollectionUtils.isEmpty(users) && users.size() >= 3) {
            // To check the number of Active users
            List<User> confirmedUsers =  users.stream().filter(userInside -> DfcConstants.USER_STATUS.ACTIVE.name().equalsIgnoreCase(userInside.getStatus()))
                    .collect(Collectors.toList());
            Integer toBeEligibleStarId = getToBeEligibleStarId(parentUser);
            if (!CollectionUtils.isEmpty(confirmedUsers) && confirmedUsers.size() >= DfcConstants.RANK_PERSONAL_REFERENCE.get(toBeEligibleStarId)) {
                int count = 0 ;
                if (toBeEligibleStarId == 1) {
                    parentUser = saveUser(parentUser, toBeEligibleStarId);
                    userTubeHelper.createUserTubeRecord(parentUser);
                    isParentChanged = true;
                } else {
                    checkStarCount(DfcConstants.RANK_IN_DIRECT_TEAM.get(toBeEligibleStarId).keySet().stream().findFirst().get(), users, count, false);
                    if (count >= DfcConstants.RANK_IN_DIRECT_TEAM.get(toBeEligibleStarId).keySet().iterator().next()) {
                        parentUser = saveUser(user, toBeEligibleStarId);
                        isParentChanged = true;
                    }
                }
            }

          /*  if (null == user.getStar()) {
                if (!CollectionUtils.isEmpty(confirmedUsers) && confirmedUsers.size() >= 3) {
                    Optional<Star> starOptional = starRepository.findById(1);
                    if(starOptional.isPresent()) {
                        user.setStar(starOptional.get());
                        userHelper.saveUser(user);
                    }
                }
            } else if (user.getStar().getStarId() == 1) {
                if (!CollectionUtils.isEmpty(confirmedUsers) && confirmedUsers.size() >= 4) {
                    int count = 0 ;
                    checkStarCount(1, users, count, false);
                    if (count >= 2) {
                        Optional<Star> starOptional = starRepository.findById(2);
                        if(starOptional.isPresent()) {
                            user.setStar(starOptional.get());
                            userHelper.saveUser(user);
                        }
                    }
                }
            }*/
        }
        if (isParentChanged) {
            processStarEligibleRules(parentUser);
        }

    }

    private void checkStarCount(Integer starNumber, List<User> users, int count, boolean isRecursive) {
        for (User user : users) {
            if (starNumber <= user.getStar().getStarId()) {
                count++;
                if (isRecursive) break;
            }
            else {
                List<User> usersRecursive = userHelper.findByReferralId(user.getUserId());
                if (!CollectionUtils.isEmpty(usersRecursive)) {
                    checkStarCount(starNumber, usersRecursive, count, true);
                }
            }

        }
    }
}
