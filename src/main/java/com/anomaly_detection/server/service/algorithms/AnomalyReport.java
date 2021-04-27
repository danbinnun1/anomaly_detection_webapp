package com.anomaly_detection.server.service.algorithms;

public class AnomalyReport {
    private final String description;
    private final int timeStep;

    public String getDescription() {
        return description;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public AnomalyReport(String description, int timeStep) {
        this.description = description;
        this.timeStep = timeStep;
    }
}