package com.kelvingoodman.myRetail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedSkyResponse {
    private Product product;

    public RedSkyResponse withProduct(Product product) {
        setProduct(product);
        return this;
    }
}
