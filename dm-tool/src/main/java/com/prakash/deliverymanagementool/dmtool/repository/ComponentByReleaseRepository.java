package com.prakash.deliverymanagementool.dmtool.repository;

import com.prakash.deliverymanagementool.dmtool.collections.ComponentByRelease;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ComponentByReleaseRepository extends MongoRepository<ComponentByRelease, String> {

    @Query("{'jobs.jobId': ?0}")
    ComponentByRelease findByJobId(String jobId);

    List<ComponentByRelease> findByRelease(int release);

    ComponentByRelease insert(ComponentByRelease componentByRelease);

    ComponentByRelease findByComponentAndRelease(Integer id, int release);
}



