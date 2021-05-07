package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.Anomaly;
import com.anomaly_detection.server.exceptions.InvalidDataException;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TrainingNotFinishedException;
import com.anomaly_detection.server.repository.ModelRepository;
import com.anomaly_detection.server.service.algorithms.AnomalyReport;
import com.anomaly_detection.server.service.algorithms.TimeSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyAnomalyDetectionService implements AnomalyDetectionService {
    private final ModelRepository modelRepository;

    @Autowired
    public MyAnomalyDetectionService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public Anomaly detect(Map<String, List<Float>> data, String modelId) throws InvalidDataException, TrainingNotFinishedException, ModelNotFoundException {
        var optionalModel = modelRepository.findById(modelId);
        if (optionalModel.isEmpty()){
            throw new ModelNotFoundException();
        }

        var model = optionalModel.get();

        if (model.getStatus().equals("pending")) {
            throw new TrainingNotFinishedException();
        }
        //data must contain all columns the model trained on
        if (!data.keySet().containsAll(model.getColumnsNames())) {
            throw new InvalidDataException();
        }
        //if data contains additional columns the models didn't trained on, they can be ignored
        for (String key : data.keySet()) {
            if (!model.getColumnsNames().contains(key)) {
                data.remove(key);
            }
        }
        var anomalies = model.getDetector().detect(new TimeSeries(data));
        //get Anomaly object made of spans
        return getAnomaly(anomalies, model.getColumnsNames());
    }

    private Anomaly getAnomaly(List<AnomalyReport> anomalies, Set<String> columnNames) {
        Map<String, List<Span>> result = new TreeMap<>();
        for (var column : columnNames) {
            List<Integer> columnAnomalies = new ArrayList<>();
            for (AnomalyReport anomalyReport : anomalies) {
                if (column.equals(anomalyReport.getFeature1()) || column.equals(anomalyReport.getFeature2())) {
                    columnAnomalies.add(anomalyReport.getTimeStep());
                }
            }
            result.put(column, AnomalyDetectionUtil.getSpans(columnAnomalies));
        }

        return new Anomaly(result, "");
    }
}