package com.prakash.deliverymanagementool.dmtool.repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.prakash.deliverymanagementool.dmtool.collections.Build;
import com.prakash.deliverymanagementool.dmtool.collections.RecentBuildOrFailedJob;
import com.prakash.deliverymanagementool.dmtool.collections.ComponentByRelease;
import com.prakash.deliverymanagementool.dmtool.mapper.DocumentMapper;
import com.prakash.deliverymanagementool.dmtool.model.Environment;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

@Repository
@Slf4j
public class BuildRepositoryImpl implements BuildRepository {

    public static final String COMPONENT = "component";
    private final MongoTemplate mongoTemplate;

    @Autowired
    private ComponentByReleaseRepository componentByReleaseRepo;

    public BuildRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<RecentBuildOrFailedJob> findRecentBuilds(int teamId, int releaseId, String env) {
        return findAllRecentBuilds(teamId, releaseId, env, null);
    }

    @Override
    public List<RecentBuildOrFailedJob> findRecentSuccessfulBuilds(int teamId, int releaseId, String env) {
        return findAllRecentBuilds(teamId, releaseId, env, "P");
    }

    @Override
    public List<RecentBuildOrFailedJob> findFailedJobs(int teamId, int releaseId) {
        MongoCollection<Document> collection = getBuildCollection();
        Map<String, Object> matchers = createMap(teamId, releaseId, -1, null, null);
        AggregateIterable<Document> documents = getFailedJobDocuments(collection, matchers);

        return documents.map(DocumentMapper::convertToJob).into(new ArrayList<>());
    }

    @Override
    public Build findBuild(String jobId, int number) {
        Query query = Query.query(Criteria.where("jobId").is(jobId).where("number").is(number));
        return mongoTemplate.findOne(query, Build.class);
    }

    @Override
    public boolean insertBuild(ComponentByRelease component, String jobId, int number, Environment environment,
                               String status, int release) {
        if (null == component)
            component = findComponentByRelease(jobId);
        if (null == component) {
            log.error("Job id doesn't exist is {}",jobId);
            throw new IllegalStateException("The component doesn't exist in release");
        }
        Build build = Build.createBuild(component, jobId, number, environment, status, release);
        mongoTemplate.insert(build);
        return true;
    }

    @Override
    public boolean updateBuild(String jobId, int number, Environment environment, String status) {
        Query query = Query.query(Criteria.where("jobId").is(jobId).where("number").is(number));
        Update update = Update.update("env", environment).set("status", status);
        mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().upsert(false),
                Build.class);
        return true;
    }

    /*
    Assumptions: Components are already created and also exists in ComponentsByRelease
     */
    @Override
    public boolean upsertBuild(String jobId, int number, Environment environment, String status, int release) {
        ComponentByRelease component = findComponentByRelease(jobId);

        if (null == component) {
            log.error("Job id doesn't exist is {}",jobId);
            throw new IllegalStateException("The component doesn't exist in release");
        }

        Build buildOrNull = findBuild(jobId, number);
        if (null == buildOrNull)
            return insertBuild(component, jobId, number, environment, status, release);

        boolean isPromotion = !environment.equals(buildOrNull.getEnv());
        boolean isStatusUpdate = !status.equalsIgnoreCase(buildOrNull.getStatus());

        if (isPromotion || isStatusUpdate)
            return updateBuild(jobId, number, environment, status);
        return false;
    }

    private AggregateIterable<Document> getFailedJobDocuments(MongoCollection<Document> collection, Map<String, Object> matchers) {
        List<Bson> pipes = new ArrayList<>(matchers.keySet().size());
        for (Map.Entry<String, Object> pair : matchers.entrySet()) {
            pipes.add(eq(pair.getKey(), pair.getValue()));
        }

        return collection.aggregate(Arrays.asList(match(and(pipes)), lookup(
                "components",
                COMPONENT,
                "_id",
                "componentName"), lookup("teams", "team", "_id", "teamName")));
    }

    private MongoCollection<Document> getBuildCollection() {
        return mongoTemplate.getCollection("builds");
    }

    private List<RecentBuildOrFailedJob> findAllRecentBuilds(int teamId, int releaseId, String env, String status) {
        MongoCollection<Document> collection = getBuildCollection();
        int buildNumber = 0;

        Map<String, Object> matchers = createMap(teamId, releaseId, buildNumber, env, status);

        List<Bson> pipes = new ArrayList<>(matchers.keySet().size() + 1);
        for (Map.Entry<String, Object> pair : matchers.entrySet()) {
            pipes.add(eq(pair.getKey(), pair.getValue()));
        }
        pipes.add(gt("number", buildNumber));

        AggregateIterable<Document> documents = collection.aggregate(Arrays.asList(match(and(pipes)),
                group("$jobId", first("team", "$team"), first(COMPONENT,
                        "$component"), first("type", "$type"), max("recentBuild", "$number")),
                lookup("components", COMPONENT, "_id", "componentName"),
                lookup("teams", "team", "_id", "teamName")));

        return documents.map(DocumentMapper::convertToBuild).into(new ArrayList<>());
    }

    private Map<String, Object> createMap(int teamId, int releaseId, int buildNumber, String env, String status) {
        Map<String, Object> matchers = new HashMap<>();
        if (teamId != 0)
            matchers.put("team", teamId);
        if (buildNumber != 0)
            matchers.put("number", buildNumber);
        if (StringUtils.hasText(env))
            matchers.put("env", env);
        if (StringUtils.hasText(status))
            matchers.put("status", status);
        if (releaseId != 0)
            matchers.put("release", releaseId);
        return matchers;
    }

    private ComponentByRelease findComponentByRelease(String jobId) {
        return componentByReleaseRepo.findByJobId(jobId);
    }

}

