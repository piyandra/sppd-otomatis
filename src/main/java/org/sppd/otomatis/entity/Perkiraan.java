package org.sppd.otomatis.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Perkiraan {

    KREDIT_YANG_DIBERIKAN,
    DEPOSITO,
    TABUNGAN_ARISAN_SURYA,
    TABUNGAN_ATM_SURYA,
    TABUNGAN_ATM_SURYA_KHUSUS,
    TABUNGAN_SIMPEL,
    TABUNGAN_HARI_TUA,
    TABUNGAN_SURYA;

    @JsonCreator
    public static Perkiraan fromString(String value) {
        for (Perkiraan perkiraan : Perkiraan.values()) {
            if (perkiraan.name().replace("_", " ").equalsIgnoreCase(value)) {
                return perkiraan;
            }
        }
        throw new IllegalArgumentException("Invalid perkiraan name: " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name().replace("_", " ");
    }
}
