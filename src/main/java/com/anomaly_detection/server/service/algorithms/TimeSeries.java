package com.anomaly_detection.server.service.algorithms;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TimeSeries {
    private Map<String, ArrayList<Float>> ts = new TreeMap<String, ArrayList<Float>>();
    private ArrayList<String> atts = new ArrayList<String>();
    private int dataRowSize;


    public TimeSeries(String CSVfileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(CSVfileName));
        String head = reader.readLine();
        Integer j = 0;
        for (String attribute : head.split(",")) {
            ts.put(j.toString(), new ArrayList<Float>());
            atts.add(j.toString());
            j++;
        }
        String line;
        while ((line = reader.readLine()) != null) {
            int i = 0;
            for (String value : line.split(",")) {
                ts.get(atts.get(i)).add(Float.parseFloat(value));
                i++;
            }
        }
        reader.close();
        dataRowSize = ts.get(atts.get(0)).size();

    }

    public final ArrayList<Float> getAttributeData(String name) {
        return ts.get(name);
    }

    public final ArrayList<String> gettAttributes() {
        return atts;
    }

    public final int getRowSize() {
        return dataRowSize;
    }


    public TimeSeries(Map<String, ArrayList<Float>> ts) {
        this.ts = ts;
        this.atts=new ArrayList<>();
        this.atts.addAll(ts.keySet());
        dataRowSize=ts.get(atts.get(0)).size();
    }
}