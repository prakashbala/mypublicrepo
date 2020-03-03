package com.prakash.deliverymanagementool.dmtool.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;

@Data
@AllArgsConstructor
@Document(collection = "components")
public class Component {
    @Id
    private int id;
    private String component;
}
