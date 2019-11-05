package com.example.airqualityapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class MeasurementResult {
    private final String location;
    private final Parameter parameter;
    @JsonProperty("date")
    private final ResponseWrapper.DateResponse dateResponse;
    private final int value;
    private final String unit;

    MeasurementResult(String location, Parameter parameter, ResponseWrapper.DateResponse dateResponse, int value, String unit) {
        this.location = location;
        this.parameter = parameter;
        this.dateResponse = dateResponse;
        this.value = value;
        this.unit = unit;
    }

    public String getLocation() {
        return location;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public ResponseWrapper.DateResponse getDateResponse() {
        return dateResponse;
    }

    public int getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeasurementResult)) return false;

        MeasurementResult measurementResult = (MeasurementResult) o;

        if (value != measurementResult.value) return false;
        if (!location.equals(measurementResult.location)) return false;
        if (parameter != measurementResult.parameter) return false;
        if (!Objects.equals(dateResponse, measurementResult.dateResponse))
            return false;
        return unit.equals(measurementResult.unit);
    }

    @Override
    public int hashCode() {
        int result = location.hashCode();
        result = 31 * result + parameter.hashCode();
        result = 31 * result + (dateResponse != null ? dateResponse.hashCode() : 0);
        result = 31 * result + value;
        result = 31 * result + unit.hashCode();
        return result;
    }
}
