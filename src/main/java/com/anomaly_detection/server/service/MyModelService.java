package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.*;
import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TypeNotSupportedException;
import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.repository.ModelRepository;
import com.anomaly_detection.server.service.algorithms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MyModelService implements ModelService {
    private final ModelRepository modelRepository;

    private final AnomalyDetectorFactory anomalyDetectorFactory = new AnomalyDetectorFactory();
    private final ExecutorService executorService = Executors.newFixedThreadPool(threadPoolBacklog);

    private static final int threadPoolBacklog = 20;

    @Autowired
    public MyModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public ModelDto getById(String integer) throws ModelNotFoundException {
        var model = modelRepository.findById(integer);

        if (model.isEmpty()){
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

    public void insert(Model model) {
        modelRepository.insert(model);
    }

    public ModelDto delete(String modelId) throws ModelNotFoundException {
        var optionalModel = modelRepository.findById(modelId);

        if (optionalModel.isEmpty()){
            throw new ModelNotFoundException();
        }

        modelRepository.deleteById(modelId);

        return ModelMapper.toModelDto(optionalModel.get());
    }

    public ModelDto trainModel(Map<String, List<Float>> data, String type) throws TypeNotSupportedException {
        TimeSeriesAnomalyDetector anomalyDetector = anomalyDetectorFactory.createAnomalyDetector(type);

        if (anomalyDetector == null) {
            throw new TypeNotSupportedException();
        }

        var model = new Model();
        modelRepository.save(model);

        executorService.execute(() -> {
            //train model and update database
            anomalyDetector.learnNormal(new TimeSeries(data));

            model.setColumnsNames(data.keySet()).setDetector(anomalyDetector).setStatus("ready");
            modelRepository.save(model);
        });

        return ModelMapper.toModelDto(model);
    }
}
