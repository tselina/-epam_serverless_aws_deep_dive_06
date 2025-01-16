package com.task10.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Reservations {

    // Create a DynamoDbClient
    private final DynamoDbClient dbClient = DynamoDbClient.builder()
            .region(Region.of(System.getenv("region")))
            .build();

    // Create an enhanced client
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dbClient)
            .build();

    private final DynamoDbTable<Reservation> reservation = enhancedClient.table(System.getenv("reservations_table"), TableSchema.fromBean(Reservation.class));

    @JsonProperty("reservations")
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservationsFromDb() {
        reservation.scan()
                .items()
                .forEach(this::addReservation);
        return reservations;
    }

    public static boolean isOverlapping(Reservation nr) {
        LocalTime nrStartTime = LocalTime.parse(nr.getSlotTimeStart());
        LocalTime nrEndTime = LocalTime.parse(nr.getSlotTimeEnd());

        Reservations reservations1 = new Reservations();
        return reservations1.getReservationsFromDb().stream()
                .anyMatch((Reservation r) -> {
                    LocalTime rStartTime = LocalTime.parse(r.getSlotTimeStart());
                    LocalTime rEndTime = LocalTime.parse(r.getSlotTimeEnd());

                    return Objects.equals(r.getDate(), nr.getDate()) &&
                            (
                                (nrStartTime.isAfter(rStartTime) && nrStartTime.isBefore(rEndTime))
                                        || (nrEndTime.isAfter(rStartTime) && nrEndTime.isBefore(rEndTime))

                            );
                } );
    }
}
