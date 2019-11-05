package com.example.airqualityapi.domain;

import com.example.airqualityapi.model.Parameter;
import com.example.airqualityapi.model.AirQualityResults;
import com.example.airqualityapi.service.RestServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
class BaseController {

    @Autowired
    private RestServiceV1 service;

    @GetMapping("locations/city/{city}")
    public ResponseEntity<Object> getLocations(@PathVariable String city) {
        return new ResponseEntity<>(service.getAvailableLocations(city), HttpStatus.OK);
    }

    @GetMapping("today/parameter/{parameter}")
    public ResponseEntity<?> getTodayLevels(@PathVariable Parameter parameter, @RequestParam(value = "city") String city) {
        AirQualityResults todayAirQualityResults = service.getTodayValues(parameter, city);
        return new ResponseEntity<>(todayAirQualityResults, HttpStatus.OK);
    }
}
