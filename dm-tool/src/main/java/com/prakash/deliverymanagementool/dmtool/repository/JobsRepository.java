package com.prakash.deliverymanagementool.dmtool.repository;

import com.prakash.deliverymanagementool.dmtool.collections.ComponentByRelease;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends MongoRepository<ComponentByRelease, Integer> {
    List<ComponentByRelease> findByRelease(int release);
    int countByRelease(int i);
}
