package com.anomaly_detection.server.dto;

import com.anomaly_detection.server.model.Model;

public class ModelMapper {
    public static ModelDto toModelDto(Model model) {
        return new ModelDto(model.getId(), model.getInsertionTime(), model.getStatus());
    }
}
