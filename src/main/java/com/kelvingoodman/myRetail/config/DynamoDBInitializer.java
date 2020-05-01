package com.kelvingoodman.myRetail.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.kelvingoodman.myRetail.dao.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DynamoDBInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    @Autowired
    DynamoDBMapper dynamoDBMapper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final String[] localArgs = {"-inMemory"};
        DynamoDBProxyServer server = null;
        try {
            System.setProperty("sqlite4java.library.path", "native-libs");
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();
            populatePriceTable();
            System.out.println("found " + amazonDynamoDB.listTables().getTableNames());
        } catch (Exception e) {
            //TODO use real logger
            System.out.println("DynamoDb failed to start: " + e.toString());
        }
    }

    private void populatePriceTable() {
        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(ProductPrice.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);

        //Remove!
        ProductPrice productPrice = new ProductPrice().withId(13860428).withPrice(BigDecimal.valueOf(321)).withCurrencyCode("USD");
        dynamoDBMapper.save(productPrice);
    }
}

