package com.kelvingoodman.myRetail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private Item item;

    public Product withItem(Item item) {
        setItem(item);
        return this;
    }
}
