package com.kelvingoodman.myRetail.repository.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kelvingoodman.myRetail.dao.ProductPrice;
import com.kelvingoodman.myRetail.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceRepositoryImpl implements ProductPriceRepository {
    @Autowired
    DynamoDBMapper dynamoDBMapper;

    @Override
    public ProductPrice getPrice(int id) {
        return dynamoDBMapper.load(ProductPrice.class, id);
    }

    @Override
    public void savePrice() {

    }
}
