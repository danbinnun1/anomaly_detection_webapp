package com.anomaly_detection.server.service.algorithms;

import java.io.Closeable;
import java.util.ArrayList;

public abstract class TimeSeriesAnomalyDetector {
    public abstract void learnNormal(final TimeSeries ts);

    public abstract ArrayList<AnomalyReport> detect(final TimeSeries ts);

}