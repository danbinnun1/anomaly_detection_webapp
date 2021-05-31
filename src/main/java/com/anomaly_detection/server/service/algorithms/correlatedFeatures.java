package com.anomaly_detection.server.service.algorithms;


public class correlatedFeatures {
    public String feature1; // names of the correlated features
    public String feature2;
    public float correlation;
    public Line lin_reg = new Line();
    public float threshold;
    public float cx;
    public float cy;
}