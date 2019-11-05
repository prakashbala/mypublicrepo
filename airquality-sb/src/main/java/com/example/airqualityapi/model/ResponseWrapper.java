package com.example.airqualityapi.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ResponseWrapper {

    class DateResponse {
        private final LocalDateTime local;

        DateResponse(LocalDateTime local) {
            this.local = local;
        }

        public LocalDateTime getLocal() {
            return local;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DateResponse)) return false;

            DateResponse that = (DateResponse) o;

            return local.equals(that.local);
        }

        @Override
        public int hashCode() {
            return local.hashCode();
        }
    }

    private final List<MeasurementResult> measurementResults;

    ResponseWrapper(List<MeasurementResult> measurementResults) {
        this.measurementResults = measurementResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseWrapper)) return false;

        ResponseWrapper that = (ResponseWrapper) o;

        return Objects.equals(measurementResults, that.measurementResults);
    }

    @Override
    public int hashCode() {
        return measurementResults != null ? measurementResults.hashCode() : 0;
    }

    public List<MeasurementResult> getMeasurementResults() {
        return measurementResults;
    }

}
