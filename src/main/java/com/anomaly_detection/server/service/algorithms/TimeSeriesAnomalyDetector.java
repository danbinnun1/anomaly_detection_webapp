package com.anomaly_detection.server.service.algorithms;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public interface TimeSeriesAnomalyDetector {
    void learnNormal(TimeSeries ts);
    List<AnomalyReport> detect(TimeSeries ts);
}