package com.example.airqualityapi.model;

import com.example.airqualityapi.domain.AQCategoryPicker;
import com.example.airqualityapi.domain.AQColorPicker;

import java.util.List;

public class AirQualityResults {

    private final List<AirQualityValue> values;
    private final String city;
    private AQCategoryPicker.Category category;
    private AQColorPicker.Color color;

    public AirQualityResults(List<AirQualityValue> values, String city) {
        this.values = values;
        this.city = city;
    }

    public AQCategoryPicker.Category getCategory() {
        return category;
    }

    public void setCategory(AQCategoryPicker.Category category) {
        this.category = category;
    }

    public AQColorPicker.Color getColor() {
        return color;
    }

    public void setColor(AQColorPicker.Color color) {
        this.color = color;
    }

    public List<AirQualityValue> getValues() {
        return values;
    }
}
