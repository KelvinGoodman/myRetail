package com.kelvingoodman.myRetail.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Setter;

import java.math.BigDecimal;

@DynamoDBTable(tableName = "ProductPricing")
@Setter
public class Product {
    private String id;
    private BigDecimal price;
    private String currencyCode;

    @DynamoDBHashKey
    public String getId() {
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
}
