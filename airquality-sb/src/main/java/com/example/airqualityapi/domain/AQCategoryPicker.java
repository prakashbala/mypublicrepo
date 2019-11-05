package com.example.airqualityapi.domain;

import com.example.airqualityapi.model.AirQualityValue;

import java.util.Arrays;
import java.util.List;

public class AQCategoryPicker {
    public enum Category {
        BEYOND_INDEX(0),
        HAZARDOUS(1),
        VERY_UNHEALTHY(2),
        UNHEALTHY(3),
        UNHEALTHY_FOR_SENSITIVE(4),
        MODERATE(5),
        GOOD(6);

        int value;

        Category(int value) {
            this.value = value;
        }

        public static Category fromValue(int result) {
            Category category = null;
            for (Category c : Category.values()) {
                if (result == c.toInt()) {
                    category = c;
                }
            }
            return category;
        }

        public int toInt() {
            return value;
        }
    }

    public Category findCategory(List<AirQualityValue> values) {
        Category pm25Category = null;
        Category pm10Category = null;

        for (AirQualityValue v : values) {
            int value = v.getValue();
            switch (v.getParameter()) {
                case PM25:
                    pm25Category = findPM25Category(value);
                    break;
                case PM10:
                    pm10Category = findPM10Category(value);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getParameter());
            }
        }

        return highestOf(pm25Category, pm10Category);
    }

    private Category highestOf(Category... categories) {
        int result = Arrays.stream(categories).map(Category::toInt).max((o1, o2) -> 0).get();
        return Category.fromValue(result);
    }

    private Category findPM25Category(int value) {
        if (value > 15000) {
            return Category.BEYOND_INDEX;
        } else if (value > 5000) {
            return Category.HAZARDOUS;
        } else if (value > 2000) {
            return Category.UNHEALTHY;
        } else {
            return Category.GOOD;
        }
    }

    private Category findPM10Category(int value) {
        if (value > 20000) {
            return Category.VERY_UNHEALTHY;
        } else if (value > 10000) {
            return Category.UNHEALTHY_FOR_SENSITIVE;
        } else if (value > 5000) {
            return Category.MODERATE;
        } else {
            return Category.GOOD;
        }
    }
}
