package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.TypeNotSupportedException;

import java.util.List;
import java.util.Map;

public interface ModelTrainingService {
    ModelDto trainModel(Map<String, List<Float>> data, String type) throws TypeNotSupportedException;
}
