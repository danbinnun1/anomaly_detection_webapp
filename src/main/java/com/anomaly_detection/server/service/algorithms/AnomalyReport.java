package com.anomaly_detection.server.service.algorithms;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AnomalyReport {
    private final String feature1;
    private final String feature2;
    private final int timeStep;
}