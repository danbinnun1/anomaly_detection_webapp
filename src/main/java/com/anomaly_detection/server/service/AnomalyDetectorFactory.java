package com.anomaly_detection.server.service;

import com.anomaly_detection.server.service.algorithms.HybridAnomalyDetector;
import com.anomaly_detection.server.service.algorithms.SimpleAnomalyDetector;
import com.anomaly_detection.server.service.algorithms.TimeSeriesAnomalyDetector;

import java.util.HashMap;
import java.util.Map;

public class AnomalyDetectorFactory {

    private interface DetectorFactory {
        TimeSeriesAnomalyDetector createDetector();
    }

    private final Map<String, DetectorFactory> factoriesMap;

    public AnomalyDetectorFactory() {
        factoriesMap = new HashMap<>();

        factoriesMap.put("regression", SimpleAnomalyDetector::new);
        factoriesMap.put("hybrid", HybridAnomalyDetector::new);
    }

    public TimeSeriesAnomalyDetector createAnomalyDetector(String key) {
        if (!factoriesMap.containsKey(key)) {
            return null;
        }

        return factoriesMap.get(key).createDetector();
    }
}
