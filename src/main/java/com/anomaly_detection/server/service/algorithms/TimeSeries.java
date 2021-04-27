package com.anomaly_detection.server.service.algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;


public class TimeSeries {
    private ArrayList<ArrayList<Float>> ts = new ArrayList<ArrayList<Float>>();

    public TimeSeries(String CSVfileName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(CSVfileName));
        String head = reader.readLine();
        for (String attribute : head.split(",")) {
            ts.add(new ArrayList<>());
        }
        String line;
        while ((line = reader.readLine()) != null) {
            int i = 0;
            for (String value : line.split(",")) {
                ts.get(i).add(Float.parseFloat(value));
                i++;
            }
        }
        reader.close();

    }

    public ArrayList<ArrayList<Float>> getTs() {
        return ts;
    }
}