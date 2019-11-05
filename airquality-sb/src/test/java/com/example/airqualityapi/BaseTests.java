package com.example.airqualityapi;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class BaseTests {

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

}
