package com.prakash.deliverymanagementool.dmtool.utils;

public class Utils {

    public static int findCodeOrConfig(String jobId) {
        if (jobId.contains("config") && (jobId.contains("paas2") || jobId.contains("paasv2") || jobId.contains("v2"))) {
            return 3;
        } else if (jobId.contains("config")) {
            return 2;
        }
        return 1;
    }
}
