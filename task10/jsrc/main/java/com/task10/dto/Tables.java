package com.task10.dto;

import lombok.Data;

import java.util.List;

@Data
public class Tables {
    private List<Table> tables;

    public void addTable(Table table) {
        tables.add(table);
    }
}
