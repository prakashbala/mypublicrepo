package com.prakash.deliverymanagementool.dmtool.model;

import java.util.Arrays;

public enum TypeEnum {
    CODE(1), CONFIG(2), V2CONFIG(3);

    private int value;

    TypeEnum(int value) {
        this.value = value;
    }

    public static TypeEnum findTypeByValue(int value) {
        return Arrays.stream(TypeEnum.values())
                .filter(v -> v.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid enum value"));
    }
}
