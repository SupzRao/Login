package com.cidaassdk;

/**
 * Created by Suprada on 12-Apr-17.
 */

public enum UserStatus {
    PENDING("PENDING"),
    VERIFIED("VERIFIED"),
    DECLINED("DECLINED"),
    COMBINED("COMBINED");

    UserStatus(String combined) {

    }
}
