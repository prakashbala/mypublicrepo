package com.prakash.deliverymanagementool.dmtool.repository;

import com.prakash.deliverymanagementool.dmtool.collections.Build;
import com.prakash.deliverymanagementool.dmtool.collections.RecentBuildOrFailedJob;
import com.prakash.deliverymanagementool.dmtool.collections.ComponentByRelease;
import com.prakash.deliverymanagementool.dmtool.model.Environment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildRepository {
    List<RecentBuildOrFailedJob> findRecentBuilds(int teamId, int releaseId, String env);

    List<RecentBuildOrFailedJob> findRecentSuccessfulBuilds(int teamId, int releaseId, String env);

    List<RecentBuildOrFailedJob> findFailedJobs(int teamId, int releaseId);

    Build findBuild(String jobId, int number);

    boolean insertBuild(ComponentByRelease component, String jobId, int number, Environment environment,
                        String status,
                        int release);

    boolean updateBuild(String jobId, int number, Environment environment, String status);

    boolean upsertBuild(String jobId, int number, Environment environment, String status, int release);

}

