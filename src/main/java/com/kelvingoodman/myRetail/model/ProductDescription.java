package com.kelvingoodman.myRetail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDescription {
    private String title;

    public ProductDescription withTitle(String title) {
        setTitle(title);
        return this;
    }
}
