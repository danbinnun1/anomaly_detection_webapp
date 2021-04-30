package com.anomaly_detection.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Anomaly {
    private Map<String, List<Span>> anomalies;
    private String reason;
}
