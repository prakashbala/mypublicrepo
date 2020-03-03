package com.prakash.deliverymanagementool.dmtool.model;

import com.prakash.deliverymanagementool.dmtool.collections.Job;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ComponentByReleaseDetails {
    private String componentName;
    private String teamName;
    private int release;
    private List<JobDetails> jobs;
}

