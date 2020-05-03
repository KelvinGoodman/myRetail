package com.kelvingoodman.myRetail.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Setter;

import java.math.BigDecimal;

@DynamoDBTable(tableName = "ProductPrice")
@Setter
public class ProductPrice {
    private Integer id;
    private BigDecimal price;
    private String currencyCode;

    @DynamoDBHashKey
    public Integer getId() {
        return id;
    }

    @DynamoDBAttribute
    public BigDecimal getPrice() {
        return price;
    }

    @DynamoDBAttribute
    public String getCurrencyCode() {
        return currencyCode;
    }

    public ProductPrice withId(Integer id) {
        setId(id);
        return this;
    }

    public ProductPrice withPrice(BigDecimal price) {
        setPrice(price);
        return this;
    }

    public ProductPrice withCurrencyCode(String currencyCode) {
        setCurrencyCode(currencyCode);
        return this;
    }
}