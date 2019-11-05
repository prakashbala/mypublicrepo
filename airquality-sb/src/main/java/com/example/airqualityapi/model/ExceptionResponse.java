package com.example.airqualityapi.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

public class ExceptionResponse {
    private static final String DEFAULT = "Not available";
    private String api;

    private final String error;

    private final LocalDate timestamp;

    public ExceptionResponse(String api, String error) {
        if (api == null) {
            this.api = DEFAULT;
        }
        this.api = api;
        this.error = error;
        this.timestamp = LocalDate.now(ZoneId.of("Asia/Kolkata"));
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExceptionResponse that = (ExceptionResponse) o;

        if (!Objects.equals(api, that.api)) return false;
        return error.equals(that.error);
    }

    @Override
    public int hashCode() {
        int result = api != null ? api.hashCode() : 0;
        result = 31 * result + error.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "api='" + api + '\'' +
                ", error='" + error + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
