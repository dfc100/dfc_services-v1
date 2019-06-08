package com.dfc.network.constants;

public final class DfcConstants {

    private DfcConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String[] IGNORE_PROPERTIES = {"password", "oldPassword"};

    public static final Integer TOTAL_LEVEL = 7;

    public static final String ADMIN = "Admin";

    public enum CONFIRMATION_STATUS { CONFIRMED, PENDING }

    public enum PAID_STATUS { PAID, NOT_PAID }


}
