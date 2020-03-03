package com.prakash.deliverymanagementool.dmtool.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JobDetails {
    private String jobId;
    private TypeEnum typeEnum;
}
