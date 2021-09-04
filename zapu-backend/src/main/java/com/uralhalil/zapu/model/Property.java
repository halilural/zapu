package com.uralhalil.zapu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Document(collection = "property")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    private String id;

    @DBRef
    @Field(name = "category")
    private Category category;

    @Field(name = "title")
    private String title;

    @DBRef
    @Field(name = "city")
    private City city;

    @Field(name = "price")
    private Double price;

    @Field(name = "currency")
    private Currency currency;

}
