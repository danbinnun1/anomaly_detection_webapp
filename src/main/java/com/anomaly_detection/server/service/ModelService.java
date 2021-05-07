package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TypeNotSupportedException;
import com.anomaly_detection.server.model.Model;

import java.util.List;
import java.util.Map;

public interface ModelService {
    ModelDto getById(String integer) throws ModelNotFoundException;
    List<ModelDto> getAllModels();

    void insert(Model model);
    ModelDto delete(String modelId) throws ModelNotFoundException;

    ModelDto trainModel(Map<String, List<Float>> data, String type) throws TypeNotSupportedException;
}
