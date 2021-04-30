package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.Anomaly;
import com.anomaly_detection.server.dto.Span;
import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.repository.ModelRepository;
import com.anomaly_detection.server.service.algorithms.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Service
public class ModelService {
    private final ModelRepository modelRepository;
    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    public Model getById(String integer) {
        //implement: if model doesn't exist throw exception, catch in controller and return 404
        return modelRepository.findById(integer).get();
    }

    public void insert(Model model) {
        modelRepository.insert(model);
    }

    public ModelDto trainModel(Map<String, ArrayList<Float>> data, String type) {
        TimeSeriesAnomalyDetector detector = null;
        if (type.equals("regression")) {
            detector = new SimpleAnomalyDetector();
        } else if (type.equals("hybrid")) {
            detector = new HybridAnomalyDetector();
        } else {

        }
        Model model = new Model();
        modelRepository.save(model);
        TimeSeriesAnomalyDetector finalDetector = detector;
        Model modelCopy = new Model(model);
        executorService.execute(() -> {
            model.setColumnsNames(data.keySet());
            finalDetector.learnNormal(new TimeSeries(data));
            model.setDetector(finalDetector);
            model.setStatus("ready");
            modelRepository.save(model);
        });
        return new ModelDto(model.getId(), model.getInsertionTime(), model.getStatus());
    }

    public Anomaly detect(Map<String, ArrayList<Float>> data, String modelId) {
        Model model = modelRepository.findById(modelId).get();
        if (!data.keySet().containsAll(model.getColumnsNames())) {
            //
        }
        for (String key : data.keySet()) {
            if (!model.getColumnsNames().contains(key)) {
                data.remove(key);
            }
        }
        ArrayList<AnomalyReport> anomalies = model.getDetector().detect(new TimeSeries(data));
        Map<String, List<Span>> result = new TreeMap<>();
        for (String column : model.getColumnsNames()) {
            List<Integer> columnAnomalies = new ArrayList<>();
            for (AnomalyReport anomalyReport : anomalies) {
                if (column.equals(anomalyReport.getFeature1()) || column
                        .equals(anomalyReport.getFeature2())) {
                    columnAnomalies.add(anomalyReport.getTimeStep());
                }
            }
            result.put(column, getSpans(columnAnomalies));
        }
        return new Anomaly(result, "");
    }

    private List<Span> getSpans(List<Integer> integers) {
        Collections.sort(integers);
        List<Span> result = new ArrayList<>();
        int lastOfSequence = -1;
        int firstOfSequence = -1;
        for (int i : integers) {
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
        return result;
    }
}
