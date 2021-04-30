package com.anomaly_detection.server.service.algorithms;

public class AnomalyReport {

    private final String feature1;
    private final String feature2;
    private final int timeStep;

    public String getFeature1() {
        return feature1;
    }

    public String getFeature2() {
        return feature2;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public AnomalyReport(String feature1, String feature2, int timeStep) {
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.timeStep = timeStep;
    }
}