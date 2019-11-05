package com.example.airqualityapi.service;

import com.example.airqualityapi.model.*;
import com.example.airqualityapi.utils.Constants;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RestServiceV1 {

    private final RestTemplate template;

    private static final Logger log = LoggerFactory.getLogger(RestServiceV1.class);

    private final Cache<String, Wrapper> localCache;

    private RestServiceV1(RestTemplate template) {
        this.template = template;
        this.localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(20, TimeUnit.SECONDS).build();
    }

    /**
     * The method retrieves all the locations where we have air metrics available in a given city
     *
     * @param city, city for which locations retrieved
     * @return list of locations air metrics available
     */
    public List<String> getAvailableLocations(String city) {
        Wrapper result = null;
        try {
            result = localCache.get(city, () -> getAirQualityMetrics(city));
        } catch (ExecutionException ee) {
            log.error("Execution exception, logging original exception", ee.getCause());
        }

        boolean isValid = null != result && null != result.getResults() && !result.getResults().isEmpty();
        if (!isValid) {
            String message = String.format("City %s is not found", city);
            ExceptionResponse exceptionResponse = new ExceptionResponse(Constants.GET_LOCATIONS_API, message);
            throw new CityNotFoundException(exceptionResponse);
        }
        return result.getResults().stream().map(Result::getLocation).collect(Collectors.toList());
    }

    private Wrapper getAirQualityMetrics(String city) {
        log.info("Getting air quality metrics for city {} from remote api", city);
        try {
            URI preparedUrl = UriComponentsBuilder.fromUriString(Constants.LOCATIONS_URL).queryParam("city", city).build().toUri();
            ResponseEntity<Wrapper> response = template.exchange(preparedUrl, HttpMethod.GET, null, Wrapper.class);
            if (!HttpStatus.OK.equals(response.getStatusCode())) {
                log.error("Unknown http status from remote api");
                throw new RuntimeException(Constants.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER);
            }
            return response.getBody();
        } catch (HttpStatusCodeException hse) {
            String exception = hse.getResponseBodyAsString();
            log.error("Exception response is {}", exception);
            throw new RuntimeException(Constants.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER);
        }
    }

    public AirQualityResults getTodayValues(Parameter parameter, String city) {
        return null;
    }
}
