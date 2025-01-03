package com.task05;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public class Request {
    private final Integer principalId;
    private final Map<String, String> content;
}
