package com.example.airqualityapi.domain;

import com.example.airqualityapi.BaseTests;
import com.example.airqualityapi.model.AirQualityResults;
import com.example.airqualityapi.model.AirQualityValue;
import com.example.airqualityapi.model.Parameter;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;

public class AirColorPickerTester extends BaseTests {
    @Mock
    private AQCategoryPicker categoryPicker;

    @InjectMocks
    private AQColorPicker colorPicker;

    @Test
    public void findColorTest() {
        //given
        ArgumentMatcher<List<AirQualityValue>> isPM25BeyondIndexLimit = isParameterBeyondLimit(Parameter.PM25, 15000);
        ArgumentMatcher<List<AirQualityValue>> isPM10BeyondIndexLimit = isParameterBeyondLimit(Parameter.PM10, 20000);
        given(categoryPicker.findCategory(AdditionalMatchers.or(argThat(isPM25BeyondIndexLimit), argThat(isPM10BeyondIndexLimit))))
                .willReturn(AQCategoryPicker.Category.BEYOND_INDEX);
        //when
        List<AirQualityValue> values = new ArrayList<>();
        AirQualityValue v1 = new AirQualityValue(Parameter.PM25, 15000);
        AirQualityValue v2 = new AirQualityValue(Parameter.PM10, 2000);
        values.add(v1);
        values.add(v2);
        AirQualityResults results = new AirQualityResults(values, "Chennai");
        AQColorPicker.Color c = colorPicker.findColor(results);
        //then
        assertEquals(AQColorPicker.Color.BROWN, c);
    }

    public ArgumentMatcher<List<AirQualityValue>> hasPM25BeyondLimit(final int count) {
        return values -> values.stream().filter(v -> Parameter.PM25.equals(v.getParameter())).findFirst().map(AirQualityValue::getValue)
                .orElse(0) >= count;
    }

    public ArgumentMatcher<List<AirQualityValue>> hasPM10BeyondLimit(final int count) {
        return values -> values.stream().filter(v -> Parameter.PM10.equals(v.getParameter())).findFirst().map(AirQualityValue::getValue)
                .orElse(0) >= count;
    }

    public ArgumentMatcher<List<AirQualityValue>> isParameterBeyondLimit(final Parameter parameter, final int count) {
        return values -> values.stream().filter(v -> parameter.equals(v.getParameter())).findFirst().map(AirQualityValue::getValue)
                .orElse(0) >= count;
    }

}
