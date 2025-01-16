package com.task10.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.task10.dto.Table;
import com.task10.dto.Tables;
import org.json.JSONObject;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class GetTableHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    // Create a DynamoDbClient
    private final DynamoDbClient dbClient = DynamoDbClient.builder()
            .region(Region.of(System.getenv("region")))
            .build();

    // Create an enhanced client
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dbClient)
            .build();

    private final DynamoDbTable<Table> table = enhancedClient.table(System.getenv("tables_table"), TableSchema.fromBean(Table.class));

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        context.getLogger().log("Tables request: " + requestEvent.getPathParameters());
        int tableId = Integer.parseInt(requestEvent.getPathParameters().get("tableId"));
        Key key = Key.builder().partitionValue(tableId).build();
        Table tableToReturn = table.getItem(key);
        context.getLogger().log("Return Table info: " + tableToReturn);

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(JSONObject.valueToString(tableToReturn));
    }

}
