package com.prakash.deliverymanagementool.dmtool.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "componentByRelease")
@AllArgsConstructor
@NoArgsConstructor
public class ComponentByRelease {
    @Id
    private String id;
    private int component;
    private int release;
    private int team;
    private List<Job> jobs;

    public ComponentByRelease(int release) {
        this.release = release;
    }

    public ComponentByRelease(int component, int release, int team, List<Job> jobs){
        this.component = component;
        this.release = release;
        this.team = team;
        this.jobs = jobs;
    }
}
