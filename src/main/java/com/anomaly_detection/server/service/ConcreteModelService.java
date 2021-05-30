package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.*;
import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ConcreteModelService implements ModelService {
    private final ModelRepository modelRepository;

    public ModelDto getById(String integer) throws ModelNotFoundException {
        var model = modelRepository.findById(integer);

        if (model.isEmpty()) {
            throw new ModelNotFoundException();
        }
        
        return ModelMapper.toModelDto(model.get());
    }

    public List<ModelDto> getAllModels() {
        List<ModelDto> dtos = new ArrayList<>();

        for(Model m : modelRepository.findAll()){
            dtos.add(ModelMapper.toModelDto(m));
        }

        return dtos;
    }

    public ModelDto delete(String modelId) throws ModelNotFoundException {
        var optionalModel = modelRepository.findById(modelId);

        if (optionalModel.isEmpty()) {
            throw new ModelNotFoundException();
        }

        modelRepository.deleteById(modelId);

        return ModelMapper.toModelDto(optionalModel.get());
    }
}
