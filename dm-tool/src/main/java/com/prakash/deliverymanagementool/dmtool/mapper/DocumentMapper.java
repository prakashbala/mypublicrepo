package com.prakash.deliverymanagementool.dmtool.mapper;

import com.prakash.deliverymanagementool.dmtool.collections.*;
import com.prakash.deliverymanagementool.dmtool.model.ComponentByReleaseDetails;
import com.prakash.deliverymanagementool.dmtool.model.JobDetails;
import com.prakash.deliverymanagementool.dmtool.model.TypeEnum;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentMapper {

    public static RecentBuildOrFailedJob convertToBuild(Document document) {
        String buildId = document.getString("_id") + "-" + document.getInteger("recentBuild");

        return constructBuildOrJob(document, buildId);
    }

    public static RecentBuildOrFailedJob convertToJob(Document document) {
        return constructBuildOrJob(document, document.getString("jobId"));
    }

    public static Component convertToComponent(Document document) {
        return new Component(document.getInteger("_id"), document.getString("component"));
    }

    public static Team convertToTeam(Document document) {
        return new Team(document.getInteger("_id"), document.getString("teamName"));
    }

    private static RecentBuildOrFailedJob constructBuildOrJob(Document document, String buildOrJobId) {
        Component component = document.getList("componentName", Document.class).stream().findFirst()
                .map(DocumentMapper::convertToComponent).orElse(null);

        Team team = document.getList("teamName", Document.class).stream().findFirst()
                .map(DocumentMapper::convertToTeam).orElse(null);

        return new RecentBuildOrFailedJob(component, team, buildOrJobId,
                TypeEnum.findTypeByValue(document.getInteger("type")));
    }

    public static ComponentByReleaseDetails convertToComponentModel(ComponentByRelease componentByRelease,
                                                                    Map<Integer, String> components
            , Map<Integer, String> teams) {
        String componentName = components.get(componentByRelease.getComponent());
        String teamName = teams.get(componentByRelease.getTeam());

        List<JobDetails> jobDets = new ArrayList<>();
        componentByRelease.getJobs().stream().map(DocumentMapper::convertToJobDetail).collect(Collectors.toList());
        return new ComponentByReleaseDetails(componentName, teamName, componentByRelease.getRelease(), jobDets);
    }

    private static JobDetails convertToJobDetail(Job job) {
        return new JobDetails(job.getJobId(), TypeEnum.findTypeByValue(job.getType()));
    }
}
