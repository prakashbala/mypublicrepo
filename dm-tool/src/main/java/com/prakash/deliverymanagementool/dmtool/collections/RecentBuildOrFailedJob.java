package com.prakash.deliverymanagementool.dmtool.collections;

import com.prakash.deliverymanagementool.dmtool.model.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecentBuildOrFailedJob {
    private Component component;
    private Team team;
    private String buildOrJobId;
    private TypeEnum type;
}


