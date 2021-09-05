package com.uralhalil.zapu.payload;

import com.uralhalil.zapu.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyResponse {

    private String id;

    private String category;

    private String title;

    private String city;

    private double price;

    private Currency currency;

    private String rootUrl;

}
