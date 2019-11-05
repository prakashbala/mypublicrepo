package com.example.airqualityapi.model;

public class CityNotFoundException extends RuntimeException {

    private final ExceptionResponse exceptionResponse;

    public CityNotFoundException(ExceptionResponse exp) {
        super(exp.getError());
        this.exceptionResponse = exp;
    }

    public ExceptionResponse getExceptionResponse() {
        return exceptionResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityNotFoundException)) return false;

        CityNotFoundException that = (CityNotFoundException) o;

        return exceptionResponse.equals(that.exceptionResponse);
    }

}
