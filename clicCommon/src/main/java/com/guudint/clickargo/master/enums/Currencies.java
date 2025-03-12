package com.guudint.clickargo.master.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum Currencies {

    USD("USD", "United States of America Dollar"),
    SGD("SGD", "Singapore Dollar"),
    IDR("IDR", "Indonesia Rupiah"),
    ;
    private final String code;
    private final String desc;

    Currencies(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Optional<Currencies> fromCode(String code) {
        return Arrays.stream(values())
                .filter(currency -> Objects.equals(currency.code, code))
                .findFirst();
    }
}
