package com.example.airqualityapi.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.List;

@Component
public class URIMatcher {

    private final List<String> uriFromDBs;

    private final Logger log = LoggerFactory.getLogger(URIMatcher.class);

    private final PathMatcher matcher = new AntPathMatcher();

    public URIMatcher() {
        uriFromDBs = callDBToRetrieveURIsAndAppendSlash();
    }

    private List<String> callDBToRetrieveURIsAndAppendSlash() {
        List<String> dbURIs = callDBToRetrieveURIs();

        //add slash at beginning and end of the URI
        List<String> modifiedURIs = new ArrayList<>();
        for (String uri : dbURIs) {
            modifiedURIs.add(addPathParamAtBothEndsIfNotAvailable(uri));
        }
        return modifiedURIs;
    }

    private List<String> callDBToRetrieveURIs() {
        List<String> stubbedURIs = new ArrayList<>();
        stubbedURIs.add("v1/cards/{param1}/test");
        stubbedURIs.add("/v2/cards/{param1}/test/");
        stubbedURIs.add("/v3/cards/{param1}/test/");
        stubbedURIs.add("v4/cards/{param2}/test");
        stubbedURIs.add("v6/cards/test");
        return stubbedURIs;
    }

    public boolean isMatching(String uri) {
        if (StringUtils.isBlank(uri))
            return false;
        //add slash at beginning and end of the URI
        uri = addPathParamAtBothEndsIfNotAvailable(uri);

        //check if matching
        for (String pattern : uriFromDBs)
            if (matcher.match(pattern, uri)) {
                log.info("Matched Pattern is {}", pattern);
                return true;
            }
        return false;
    }

    private String addPathParamAtBothEndsIfNotAvailable(String uri) {
        if (!uri.startsWith("/"))
            uri = "/".concat(uri);
        if (!uri.endsWith("/"))
            uri = uri.concat("/");
        return uri;
    }


}
