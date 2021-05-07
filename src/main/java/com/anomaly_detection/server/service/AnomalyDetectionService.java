package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.Anomaly;
import com.anomaly_detection.server.exceptions.InvalidDataException;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TrainingNotFinishedException;

import java.util.List;
import java.util.Map;

public interface AnomalyDetectionService {
    Anomaly detect(Map<String, List<Float>> data, String modelId) throws InvalidDataException, TrainingNotFinishedException, ModelNotFoundException;
}
