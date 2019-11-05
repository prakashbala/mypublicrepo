package com.example.airqualityapi;

import com.example.airqualityapi.utils.URIMatcher;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class URIMatcherTest extends BaseTests {

    @InjectMocks
    private URIMatcher matcher;

    @Test
    public void testIsMatching(){
        assertTrue(matcher.isMatching("v1/cards/123/test"));
        assertTrue(matcher.isMatching("/v1/cards/123/test"));
        assertTrue(matcher.isMatching("v1/cards/123/test/"));
        assertTrue(matcher.isMatching("/v1/cards/123/test/"));

        assertTrue(matcher.isMatching("v2/cards/123/test"));
        assertTrue(matcher.isMatching("/v2/cards/123/test"));
        assertTrue(matcher.isMatching("v2/cards/123/test/"));
        assertTrue(matcher.isMatching("/v2/cards/123/test/"));

        assertTrue(matcher.isMatching("v3/cards/213123/test"));
        assertTrue(matcher.isMatching("/v3/cards/213123/test"));
        assertTrue(matcher.isMatching("v3/cards/213123/test/"));
        assertTrue(matcher.isMatching("/v3/cards/213123/test/"));

        assertTrue(matcher.isMatching("v4/cards/213123/test"));
        assertTrue(matcher.isMatching("/v4/cards/213123/test"));
        assertTrue(matcher.isMatching("v4/cards/213123/test/"));
        assertTrue(matcher.isMatching("/v4/cards/213123/test/"));

        assertTrue(matcher.isMatching("v6/cards/test"));
        assertTrue(matcher.isMatching("/v6/cards/test"));
        assertTrue(matcher.isMatching("v6/cards/test/"));
        assertTrue(matcher.isMatching("/v6/cards/test/"));

        assertFalse(matcher.isMatching(""));
        assertFalse(matcher.isMatching(null));
        assertFalse(matcher.isMatching("3243"));

        //query param won't work in this code
        assertFalse(matcher.isMatching("v4/cards/213123/test?query=value"));
    }

}
