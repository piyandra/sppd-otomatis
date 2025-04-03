package org.sppd.otomatis.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Kantor {

    CABANG_KALIGONDANG,
    KAS_KALIKAJAR,
    KAS_KEJOBONG;

    @JsonCreator
    public static Kantor fromString(String value) {
        for (Kantor kantor : Kantor.values()) {
            if (kantor.name().replace("_", " ").equalsIgnoreCase(value)) {
                return kantor;
            }
        }
        throw new IllegalArgumentException("Invalid kantor name: " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name().replace("_", " ");
    }
}
