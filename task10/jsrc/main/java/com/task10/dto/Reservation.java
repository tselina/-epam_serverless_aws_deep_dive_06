package com.task10.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public record Reservation(UUID reservationId, int tableNumber, String clientName, String phoneNumber, String date, String slotTimeStart, String slotTimeEnd) {
    public Reservation {
        if (reservationId == null) {
            reservationId = UUID.randomUUID();
        }
    }

    public static Reservation fromJson(JSONObject jsonObject) {
        UUID reservationId;
        try {
            reservationId = UUID.fromString(jsonObject.getString("reservationId"));
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
