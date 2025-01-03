package com.task05;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class Request {
    private final Integer principalId;
    private final Map<String, String> content;
}
