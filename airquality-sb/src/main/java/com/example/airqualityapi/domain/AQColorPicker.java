package com.example.airqualityapi.domain;

import com.example.airqualityapi.model.AirQualityResults;
import org.springframework.beans.factory.annotation.Autowired;

public class AQColorPicker {

    public enum Color {
        BROWN, PURPLE, RED, ORANGE, YELLOW, GREEN;
    }

    @Autowired
    private AQCategoryPicker categoryPicker;

    public Color findColor(AirQualityResults airQualityResults) {
        AQCategoryPicker.Category category = categoryPicker.findCategory(airQualityResults.getValues());
        switch (category) {
            case BEYOND_INDEX:
                return Color.BROWN;
            case VERY_UNHEALTHY:
                return Color.PURPLE;
            case UNHEALTHY:
                return Color.RED;
            case UNHEALTHY_FOR_SENSITIVE:
                return Color.ORANGE;
            case MODERATE:
                return Color.YELLOW;
            case GOOD:
                return Color.GREEN;
        }
        return null;
    }

}
