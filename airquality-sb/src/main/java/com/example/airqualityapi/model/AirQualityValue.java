package com.example.airqualityapi.model;

public class AirQualityValue {
    private final Parameter parameter;
    private final int value;

    public AirQualityValue(Parameter parameter, int value) {
        this.parameter = parameter;
        this.value = value;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public int getValue() {
        return value;
    }
}
