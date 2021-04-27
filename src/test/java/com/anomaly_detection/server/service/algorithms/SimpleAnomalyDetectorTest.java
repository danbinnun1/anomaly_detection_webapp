package com.anomaly_detection.server.service.algorithms;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleAnomalyDetectorTest {

    @Test
    void learnNormal() throws IOException {
        SimpleAnomalyDetector s=new SimpleAnomalyDetector();
        s.learnNormal(new TimeSeries("src/test/java/com/anomaly_detection/server/service/algorithms/reg_flight.csv"));
        System.out.println(s.cf.size());
        assert s.cf.size()==11;
        TimeSeries t=new TimeSeries("src/test/java/com/anomaly_detection/server/service/algorithms/anomaly_flight.csv");
        List<AnomalyReport> r= s.detect(t);
        System.out.println(r.size());
        assert r.size()==369;
        assert s.detect(new TimeSeries("src/test/java/com/anomaly_detection/server/service/algorithms/reg_flight.csv")).size()==0;
    }
}