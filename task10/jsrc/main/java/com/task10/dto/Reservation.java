package com.task10.dto;

import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.UUID;

@Data
@DynamoDbBean
public class Reservation {

    private String reservationId;
    private int tableNumber;
    private String clientName;
    private String phoneNumber;
    private String date;
    private String slotTimeStart;
    private String slotTimeEnd;


    public Reservation() {}

    public Reservation(String reservationId, int tableNumber, String clientName, String phoneNumber, String date, String slotTimeStart, String slotTimeEnd) {
        if (reservationId == null) {
            this.reservationId = UUID.randomUUID().toString();
        } else {
            this.reservationId = reservationId;
        }
        this.tableNumber = tableNumber;
        this.clientName = clientName;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.slotTimeStart = slotTimeStart;
        this.slotTimeEnd = slotTimeEnd;
    }

    @DynamoDbPartitionKey
    public String getReservationId() {
        return reservationId;
    }

    public static Reservation fromJson(JSONObject jsonObject) {
        String reservationId;
        try {
            reservationId = jsonObject.getString("reservationId").isEmpty() ? null : jsonObject.getString("reservationId");
        } catch (JSONException e) {
            reservationId = null;
        }

        return new Reservation(
                reservationId,
                jsonObject.getInt("tableNumber"),
                jsonObject.getString("clientName"),
                jsonObject.getString("phoneNumber"),
                jsonObject.getString("date"),
                jsonObject.getString("slotTimeStart"),
                jsonObject.getString("slotTimeEnd")
        );
    }

    public static Reservation fromJson(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return fromJson(jsonObject);
    }
}
