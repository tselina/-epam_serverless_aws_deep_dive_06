package com.task10.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Tables {

    @JsonProperty("tables")
    private List<Table> tables;

    public void addTable(Table table) {
        tables.add(table);
    }
}
