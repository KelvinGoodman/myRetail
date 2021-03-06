package com.kelvingoodman.myRetail.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.kelvingoodman.myRetail.dao.ProductPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Component
public class DynamoDBInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBInitializer.class);
    /**
     * Sample of product ids known to exist in red sky
     */

    private final int[] PRODUCT_IDS = {13860416, 13860419, 13860420, 13860423, 13860424, 13860425, 13860427, 13860428, 13860429, 13860431,
            13860432, 13860433};
    @Autowired
    AmazonDynamoDB amazonDynamoDB;
    @Autowired
    DynamoDBMapper dynamoDBMapper;

    /**
     * On application context creation or refresh onApplicationEvent will create a new instance of DynamoDB in memory
     * and populate a table with know product ids and random prices. The database is destroyed as soon as the application
     * stops running, so this solution is only to demo a proof of concept
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final String[] localArgs = {"-inMemory"};
        DynamoDBProxyServer server = null;
        try {
            System.setProperty("sqlite4java.library.path", "native-libs");
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();
            populatePriceTable();
        } catch (Exception e) {
            LOGGER.error("DynamoDb failed to start: " + e.toString());
        }
    }

    /**
     * Populates ProductPrice with random pricing data for product ids that are known to exist in red sky
     */
    private void populatePriceTable() {
        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(ProductPrice.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);

        for (int productId : PRODUCT_IDS) {
            ProductPrice productPrice = new ProductPrice().withId(productId)
                    .withPrice(generateRandomPrice())
                    .withCurrencyCode("USD");
            dynamoDBMapper.save(productPrice);
        }
    }

    /**
     * @return a random price represented as BigDecimal with 2 decimal places between 10.00 and 500.00
     */
    private BigDecimal generateRandomPrice() {
        Random random = new Random();
        double minPrice = 10D;
        double maxPrice = 500D;
        double price = minPrice + random.nextDouble() * (maxPrice - minPrice);
        return new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
    }
}

