package com.kelvingoodman.myRetail.model;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private Price current_price;

    public Product withId(int id) {
        setId(id);
        return this;
    }

    public Product withName(String name) {
        setName(name);
        return this;
    }

    public Product withCurrent_price(Price current_price) {
        setCurrent_price(current_price);
        return this;
    }
}
