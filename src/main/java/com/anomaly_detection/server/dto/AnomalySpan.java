package com.anomaly_detection.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnomalySpan {
    private int start;
    private int end;
}
