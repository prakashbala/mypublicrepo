package com.prakash.deliverymanagementool.dmtool.repository;

import com.prakash.deliverymanagementool.dmtool.collections.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.net.ssl.SSLSession;

public interface TeamRepository extends MongoRepository<Team, Integer> {
    Team findByTeamName(String teamName);
}

