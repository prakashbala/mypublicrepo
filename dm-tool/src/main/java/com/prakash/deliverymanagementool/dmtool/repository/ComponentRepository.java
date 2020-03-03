package com.prakash.deliverymanagementool.dmtool.repository;

import com.prakash.deliverymanagementool.dmtool.collections.Component;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComponentRepository extends MongoRepository<Component, Integer> {
    Component findByComponent(String component);
}

