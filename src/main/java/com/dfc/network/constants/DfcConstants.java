package com.dfc.network.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DfcConstants {

    private DfcConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String[] IGNORE_PROPERTIES = {"password", "oldPassword"};

    public static final Integer TOTAL_LEVEL = 7;

    public static final String ADMIN = "Admin";

    public enum PAYMENT_STATUS { PAID, NOT_PAID, CONFIRMED }

    public enum USER_STATUS { ACTIVE, DEACTIVATED, INITATED }

    public enum USER_TUBE_STATUS { ACTIVE, INPROGRESS, COMPLETED }

    public enum USER_LOAN_STATUS {INITIATED, SANCTIONED, COMPLETED}

    public enum USER_LOAN_ELIGIBILITY_STATUS {ELIGIBLE, NOT_ELIGIBLE}

    public enum USER_LOAN_REPAYMENT_STATUS {PAID, NOTPAID, INPROGRESS, COMPLETED}

    public static final Map<Integer, Integer> RANK_PERSONAL_REFERENCE = createRankPersonalReferenceMap();

    private static Map<Integer, Integer> createRankPersonalReferenceMap() {
        Map<Integer, Integer> result = new HashMap<>();
        result.put(1, 3);
        result.put(2, 4);
        result.put(3, 6);
        result.put(4, 9);
        result.put(5, 18);
        result.put(6, 36);
        result.put(7, 72);
        return Collections.unmodifiableMap(result);
    }

    public static final Map<Integer, Map<Integer, Integer>> RANK_IN_DIRECT_TEAM = createRankInDirectTeamMap();

    private static Map<Integer, Map<Integer, Integer>> createRankInDirectTeamMap() {
        Map<Integer, Map<Integer, Integer>> result = new HashMap<>();
        result.put(2, Collections.singletonMap(1, 2));
        result.put(3, Collections.singletonMap(2, 3));
        result.put(4, Collections.singletonMap(3, 4));
        result.put(5, Collections.singletonMap(4, 5));
        result.put(6, Collections.singletonMap(5, 6));
        result.put(7, Collections.singletonMap(6, 7));
        return Collections.unmodifiableMap(result);
    }




}
