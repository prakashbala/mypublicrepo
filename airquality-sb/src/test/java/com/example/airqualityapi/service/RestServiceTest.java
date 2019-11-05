package com.example.airqualityapi.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

public class RestServiceTest {

    @Mock
    private RestTemplate restTemplate;

   /* @Spy
    private CacheConfiguration spyOnCacheConfiguration = new CacheConfiguration(0L);*/

    @Mock
    private CacheConfiguration mockedCacheConfiguration;

    @InjectMocks
    private RestService testService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //testService = new RestService(restTemplate, 5L);
        /*
        For the above method, you need to have constructor and you no longer can use @InjectMocks
         */
        //ReflectionTestUtils.setField(testService, "cacheHoldTime", 5L);
        /*
        This is preferred method if there is no constructor readily available and you don't want to create one
        for the purpose of testing.
         */
        //testService.setCacheHoldTime(5L);
        /*
        This method can be used if there is a setter available, we can move the @Value annotation to the setter
        and we call the setter
         */
        /*
        Summary: When the field is final
         */

    }

    @Test
    public void testRestTemplateNotNull() {
        assertNotNull(restTemplate);
    }
/*
    @Test
    public void testSpies() {
        doReturn(5L).when(spyOnCacheConfiguration).getCacheHoldTime();
        assertEquals(5L, testService.getCacheHoldTime());
    }*/

    @Test
    public void testMocks() {
        Mockito.when(mockedCacheConfiguration.getCacheHoldTime()).thenReturn(5L);
        assertEquals(5L, testService.getCacheHoldTime());
        verify(mockedCacheConfiguration).getCacheHoldTime();
    }


    public void getTodayValues() {
    }

    public void constructMeasurementsURL() {
    }
}