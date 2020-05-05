package com.kelvingoodman.myRetail.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.kelvingoodman.myRetail.repository")
public class DynamoDBConfig {

    @Value("${amazon.aws.accesskey}")
    private String awsAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String awsSecretKey;

    @Value("${amazon.dynamodb.endpoint}")
    private String dynamoDBEndpoint;

    @Bean
    public AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint, Regions.US_EAST_2.getName()))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .build();
        return ddb;
    }
}
