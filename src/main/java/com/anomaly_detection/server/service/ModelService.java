package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.*;
import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TypeNotSupportedException;
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
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public ModelDto getById(String integer) throws ModelNotFoundException {
        Optional<Model> model = modelRepository.findById(integer);

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
        Optional<Model> model = modelRepository.findById(modelId);
        modelRepository.deleteById(modelId);
        if (model.isEmpty()){
            throw new ModelNotFoundException();
        }
        return ModelMapper.toModelDto(model.get());
    }

    public ModelDto trainModel(Map<String, ArrayList<Float>> data, String type) throws TypeNotSupportedException {
        TimeSeriesAnomalyDetector detector = null;

        if (type.equals("regression")) {
            detector = new SimpleAnomalyDetector();
        } else if (type.equals("hybrid")) {
            detector = new HybridAnomalyDetector();
        } else {
            throw new TypeNotSupportedException();
        }

        Model model = new Model();
        modelRepository.save(model);

        TimeSeriesAnomalyDetector finalDetector = detector;
        executorService.execute(() -> {
            //train model and update database
            finalDetector.learnNormal(new TimeSeries(data));

            model.setColumnsNames(data.keySet()).setDetector(finalDetector).setStatus("ready");
            modelRepository.save(model);
        });

        return ModelMapper.toModelDto(model);
    }
}
