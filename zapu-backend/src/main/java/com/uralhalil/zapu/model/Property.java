package com.uralhalil.zapu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Builder
@Document(collection = "property")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    private String id;

    @NotNull(message = "Kategori alanı zorunludur!")
    @Field(name = "category_id")
    private String category;

    @NotNull(message = "Başlık alanı zorunludur!")
    @Field(name = "title")
    private String title;

    @NotNull(message = "Şehir alanı zorunludur!")
    @Field(name = "city_id")
    private String city;

    @NotNull(message = "Fiyat alanı zorunludur!")
    @Field(name = "price")
    private double price;

    @NotNull(message = "Para birimi alanı zorunludur!")
    @Field(name = "currency")
    private Currency currency;

    public Property(String id, String title, Double price, Currency currency) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.currency = currency;
    }
}
