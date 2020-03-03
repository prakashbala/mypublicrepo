package com.prakash.deliverymanagementool.dmtool.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "teams")
public class Team {
    @Id
    private int id;
    private String teamName;
}
