package com.kelvingoodman.myRetail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private ProductDescription product_description;

    public Item withProductDescription(ProductDescription productDescription) {
        setProduct_description(productDescription);
        return this;
    }
}
