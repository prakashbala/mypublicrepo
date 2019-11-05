package com.example.airqualityapi.service;

import com.example.airqualityapi.model.*;
import com.example.airqualityapi.utils.Constants;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
class CacheConfiguration {
    private final long cacheHoldTime;

    public CacheConfiguration(@Value("${cache.hold_time}")long cacheHoldTime) {
        this.cacheHoldTime = cacheHoldTime;
    }

    public long getCacheHoldTime() {
        return cacheHoldTime;
    }
}

@Service
public class RestService {

    @Autowired
    private RestTemplate template;

    @Autowired
    private CacheConfiguration cacheConfiguration;

    @Value("${cache.hold_time}")
    private final long cacheHoldTime = 0L;

    private static final Logger log = LoggerFactory.getLogger(RestService.class);

    private Cache<String, Wrapper> localCache;

    @PostConstruct
    private void postInit(){
        this.localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(cacheHoldTime, TimeUnit.MINUTES).build();
    }

   /* public RestService() {
       this.template = new RestTemplate();
       this.cacheHoldTime = 8L;
       this.localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(cacheHoldTime, TimeUnit.MINUTES).build();
    }

    public RestService(RestTemplate template, @Value("${cache.hold_time}") long cacheHoldTimeInMinutes) {
        this.template = template;
        this.cacheHoldTime = cacheHoldTimeInMinutes;
        this.localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(cacheHoldTimeInMinutes, TimeUnit.MINUTES).build();
    }*/

    //https://api.openaq.org/v1/measurements?country=IN&order_by=date&sort=desc&parameter=pm25&date_from=2019-11-01&city=Chennai
    public List<AirQualityResults> getTodayValues(Parameter parameter, String city) {

        MultiValueMap<String, String> qp = new LinkedMultiValueMap<>();
        qp.add("country", "IN");
        qp.add("date_from", LocalDate.now(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.BASIC_ISO_DATE));
        qp.add("order_by", "value");
        qp.add("sort", "desc");
        qp.add("city", city);

        URI url = UriComponentsBuilder.fromHttpUrl(Constants.MEASUREMENTS_URL).queryParams(qp).build().toUri();

        ResponseEntity<ResponseWrapper> responseWrapper = template.getForEntity(url, ResponseWrapper.class);

        ResponseWrapper wrapper = responseWrapper.getBody();
        List<MeasurementResult> results = wrapper.getMeasurementResults();

        List<AirQualityResults> airQualityValues = new ArrayList<>();
        for (MeasurementResult m : results) {
        }

        return airQualityValues;
    }

    public URI constructMeasurementsURL(String city, Parameter parameter) {
        return null;
    }

    public long getCacheHoldTime() {
        return cacheConfiguration.getCacheHoldTime();
    }

}
