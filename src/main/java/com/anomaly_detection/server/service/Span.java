package com.anomaly_detection.server.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Span {
    private int start;
    private int end;
}
