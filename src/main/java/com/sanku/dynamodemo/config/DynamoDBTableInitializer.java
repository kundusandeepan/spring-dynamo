package com.sanku.dynamodemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

import jakarta.annotation.PostConstruct;

@Configuration
public class DynamoDBTableInitializer {

    private final AmazonDynamoDB amazonDynamoDB;

    public DynamoDBTableInitializer(@Autowired AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
    }

    @PostConstruct
    public void init() {
        
        checkAndCreateTable("TestTable");
    }

    private void checkAndCreateTable(String tableName) {
        if (!doesTableExist(tableName)) {
            createTable(tableName);
        }
    }

    private boolean doesTableExist(String tableName) {
        try {
            TableDescription tableDescription = amazonDynamoDB.describeTable(tableName).getTable();
            return tableDescription.getTableStatus().equals(TableStatus.ACTIVE.toString());
        } catch (Exception e) {
            return false;
        }
    }

    private void createTable(String tableName) {
        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
                .withAttributeDefinitions(new com.amazonaws.services.dynamodbv2.model.AttributeDefinition("id", com.amazonaws.services.dynamodbv2.model.ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L));

        amazonDynamoDB.createTable(request);
    }
}