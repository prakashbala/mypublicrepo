package com.prakash.deliverymanagementool.dmtool.collections;

import com.prakash.deliverymanagementool.dmtool.model.Environment;
import com.prakash.deliverymanagementool.dmtool.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "builds")
public class Build {
    @Id
    private String id;
    private String jobId;
    private int team;
    private int number;
    private int component;
    private Environment env;
    private int type;
    private String status;
    private int release;

    public static Build createBuild(ComponentByRelease component, String jobId, int number, Environment env, String status,
                                    int release){
        Build build = new Build();
        build.setComponent(component.getComponent());
        build.setTeam(component.getTeam());
        build.setJobId(jobId);
        build.setRelease(release);
        build.setStatus(status);
        build.setType(Utils.findCodeOrConfig(jobId));
        build.setEnv(env);
        build.setNumber(number);
        return build;
    }
}
