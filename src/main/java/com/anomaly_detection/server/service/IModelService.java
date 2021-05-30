package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;

import java.util.List;

public interface IModelService {
    ModelDto getById(String integer) throws ModelNotFoundException;
    List<ModelDto> getAllModels();

    ModelDto delete(String modelId) throws ModelNotFoundException;
}
