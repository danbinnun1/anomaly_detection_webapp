package com.anomaly_detection.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnomalyDetectionUtil {

    public static List<Span> getSpans(List<Integer> integers) {
        List<Integer> sortedList = new ArrayList<>(integers);
        Collections.sort(sortedList);

        List<Span> result = new ArrayList<>();
        int lastOfSequence = -1;
        int firstOfSequence = -1;
        for (int i : sortedList) {
            if (firstOfSequence == -1) {
                firstOfSequence = i;
                lastOfSequence = i;
            } else if (i == lastOfSequence + 1) {
                lastOfSequence++;
            } else {
                result.add(new Span(firstOfSequence, lastOfSequence + 1));
                firstOfSequence = i;
                lastOfSequence = i;
            }
        }
        if (firstOfSequence != -1){
            result.add(new Span(firstOfSequence, lastOfSequence + 1));
        }

        return result;
    }
}
