package com.uralhalil.zapu.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Document(collection = "root_url_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RootUrlConfig {

    @Id
    private String id;

    @Field(name = "priority")
    private int priority;

    @Field(name = "parameter_name")
    private String parameterName;

    @Field(name = "up_parameter_name")
    private String upParameterName;

    @Field(name = "repository_name")
    private String repositoryName;

}
