package com.uralhalil.zapu.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Document(collection = "city")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

}
