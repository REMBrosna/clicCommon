package com.guudint.clickargo.payment.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum PaymentProvider {
    BANK_NEGARA_INDONESIA("BCI", "Bank Negara Indonesia"),
    BANK_CENTRAL_ASIA("BCA", "Bank Central Asia"),
    BANK_MANDIRI("MANDIRI", "Bank Mandiri"),
    BANK_DANAMON("DNO", "Bank Danamon"),
    ;
    private final String code;
    private final String desc;

    PaymentProvider(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Optional<PaymentProvider> fromCode(String code) {
        return Arrays.stream(values()).filter(pp -> StringUtils.equalsIgnoreCase(pp.code, code)).findFirst();
    }

}
