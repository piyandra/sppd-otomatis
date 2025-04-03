package org.sppd.otomatis.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Bank {
    MANDIRI,
    BRI,
    BCA,
    BNI,
    BANK_JATENG,
    DANAMON;

    @JsonCreator
    public static Bank fromString(String value) {
        return Arrays.stream(Bank.values())
                .filter(bank -> bank.name().replace("_", " ").equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid bank name: " + value));
    }
    @JsonValue
    public String toValue() {
        return this.name().replace("_", " ");
    }

}
