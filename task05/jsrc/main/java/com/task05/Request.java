package com.task05;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public class Request {
    private Integer principalId;
    private Map<String, String> content;
}
