package com.task10.dto;

import org.json.JSONException;
import org.json.JSONObject;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public record Table(@DynamoDbPartitionKey int id, int number, int places, boolean isVip, int minOrder) {

    public Table {
        if (id == 0 || number == 0 || places == 0) {
            throw new IllegalArgumentException("Missing or incomplete data.");
        }
    }

    public static Table fromJson(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        int number = jsonObject.getInt("number");
        int places = jsonObject.getInt("places");
        boolean isVip;
        try {
            isVip = jsonObject.getBoolean("isVip");
        } catch (JSONException e) {
            isVip = false;
        }
        int minOrder;
        try {
            minOrder = jsonObject.getInt("minOrder");
        } catch (JSONException e) {
            minOrder = 0;
        }

        return new Table(id, number, places, isVip, minOrder);
    }

    public static Table fromJson(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return fromJson(jsonObject);
    }
}
