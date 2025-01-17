package com.task10.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tables {

    // Create a DynamoDbClient
    @JsonIgnore
    private final DynamoDbClient dbClient = DynamoDbClient.builder()
            .region(Region.of(System.getenv("region")))
            .build();

    // Create an enhanced client
    @JsonIgnore
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dbClient)
            .build();

    @JsonIgnore
    private final DynamoDbTable<Table> table = enhancedClient.table(System.getenv("tables_table"), TableSchema.fromBean(Table.class));

    @JsonProperty("tables")
    private List<Table> tables = new ArrayList<>();

    public void addTable(Table table) {
        tables.add(table);
    }

    public List<Table> getTablesFromDb() {
        table.scan()
                .items()
                .forEach(this::addTable);
        return getTables();
    }

    public static boolean doesTableExist(int id) {
        Tables tables1 = new Tables();
        return tables1.getTablesFromDb().stream().anyMatch((Table t) -> t.getId() == id);
    }
}
