package com.prakash.deliverymanagementool.dmtool.service;

import com.prakash.deliverymanagementool.dmtool.collections.Component;
import com.prakash.deliverymanagementool.dmtool.collections.ComponentByRelease;
import com.prakash.deliverymanagementool.dmtool.collections.Job;
import com.prakash.deliverymanagementool.dmtool.collections.Team;
import com.prakash.deliverymanagementool.dmtool.mapper.DocumentMapper;
import com.prakash.deliverymanagementool.dmtool.model.ComponentByReleaseDetails;
import com.prakash.deliverymanagementool.dmtool.repository.BuildRepository;
import com.prakash.deliverymanagementool.dmtool.repository.ComponentByReleaseRepository;
import com.prakash.deliverymanagementool.dmtool.repository.ComponentRepository;
import com.prakash.deliverymanagementool.dmtool.repository.TeamRepository;
import com.prakash.deliverymanagementool.dmtool.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeliveryManagementService {

    @Autowired
    private ComponentRepository componentRepo;

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private ComponentByReleaseRepository componentByReleaseRepo;

    @Autowired
    private BuildRepository buildRepo;

    public String findTeamNameById(Integer id) {
        return teamRepo.findById(id).map(Team::getTeamName).orElse(null);
    }

    public String findComponentNameById(Integer id) {
        return componentRepo.findById(id).map(Component::getComponent).orElse(null);
    }

    public List<ComponentByReleaseDetails> findAllComponentsOfRelease(int release) {
        List<ComponentByRelease> allComponentsFromRelease = componentByReleaseRepo.findByRelease(2002);
        return allComponentsFromRelease.stream().map(a -> DocumentMapper.convertToComponentModel(a, getComponents(),
                getTeams())).collect(Collectors.toList());
    }

    public Map<Integer, String> getTeams() {
        List<Team> teams = teamRepo.findAll();
        Map<Integer, String> teamMap = new HashMap<>();
        for (Team a : teams) {
            teamMap.put(a.getId(), a.getTeamName());
        }
        return teamMap;
    }

    public Map<Integer, String> getComponents() {
        List<Component> components = componentRepo.findAll();
        Map<Integer, String> componentMap = new HashMap<>();
        for (Component a : components) {
            componentMap.put(a.getId(), a.getComponent());
        }
        return componentMap;
    }

    public boolean insertComponentForRelease(String componentName, int release, String teamName) {
        Integer componentId = componentRepo.findByComponent(componentName).getId();
        Integer teamId = teamRepo.findByTeamName(teamName).getId();

        ComponentByRelease componentByRelease = new ComponentByRelease(componentId, release, teamId,
                Collections.emptyList());
        componentByReleaseRepo.insert(componentByRelease);
        return true;
    }

    public boolean updateJobsInComponentForRelease(String componentName, int release, String jobId) {
        Integer componentId = componentRepo.findByComponent(componentName).getId();
        ComponentByRelease componentByRelease = componentByReleaseRepo.findByComponentAndRelease(componentId, release);

        List<Job> jobs = componentByRelease.getJobs();
        if (jobs.contains(jobId))
            return true;
        jobs.add(new Job(jobId, Utils.findCodeOrConfig(jobId)));
        componentByReleaseRepo.save(componentByRelease);
        return true;
    }

}
