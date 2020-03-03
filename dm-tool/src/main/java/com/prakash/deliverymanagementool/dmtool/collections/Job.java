package com.prakash.deliverymanagementool.dmtool.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    private String jobId;
    private int type;
}
