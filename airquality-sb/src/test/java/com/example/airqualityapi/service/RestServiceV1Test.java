package com.example.airqualityapi.service;

import com.example.airqualityapi.model.CityNotFoundException;
import com.example.airqualityapi.model.ExceptionResponse;
import com.example.airqualityapi.model.Result;
import com.example.airqualityapi.model.Wrapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;

/*
This is JUnit4 test case
 */

public class RestServiceV1Test {

    private static final String CITY_INVALID = "Gotham";
    private static final String LOCATIONS_URL_STUB = "https://api.openaq.org/v1/locations";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private com.example.airqualityapi.service.RestServiceV1 testObject;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = CityNotFoundException.class)
    public void getAllLocations_whenCityIsInvalid_returnCityNotFoundExp() {
        ResponseEntity<Wrapper> mockedResult = getWrapperResponseEntityMocked();
        //given this input
        given(restTemplate.exchange(eq(getUri()), eq(HttpMethod.GET), isNull(), eq(Wrapper.class)))
                .willReturn(mockedResult);

        //when
        testObject.getAvailableLocations(CITY_INVALID);

        //then, CityNotFoundException

    }

    @Test
    public void getAllLocations_whenCityIsInvalid_returnCityNotFoundExp_usingRule() {
        ResponseEntity<Wrapper> mockedResult = getWrapperResponseEntityMocked();
        //given this input
        given(restTemplate.exchange(eq(getUri()), eq(HttpMethod.GET), isNull(), eq(Wrapper.class)))
                .willReturn(mockedResult);

        //when
        String errorMessage = String.format("City %s is not found", CITY_INVALID);
        ExceptionResponse expectedExceptionRes = new ExceptionResponse("locations/city/{city}", errorMessage);

        thrown.expect(is(new CityNotFoundException(expectedExceptionRes)));

        testObject.getAvailableLocations(CITY_INVALID);
    }

    private ResponseEntity<Wrapper> getWrapperResponseEntityMocked() {
        List<Result> locations = new ArrayList<>();
        locations.add(new Result());
        return new ResponseEntity<>(new Wrapper(), HttpStatus.OK);
    }

    private URI getUri() {
        return UriComponentsBuilder.fromUriString(LOCATIONS_URL_STUB).queryParam("city", CITY_INVALID)
                .build().toUri();
    }

}