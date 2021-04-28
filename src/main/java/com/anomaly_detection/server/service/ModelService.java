package com.anomaly_detection.server.service;

import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.repository.ModelRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Service
public class ModelService {
    private final ModelRepository modelRepository;
    private ExecutorService executorService= Executors.newFixedThreadPool(20);

    public Model getById(Integer integer){
        //implement: if model doesn't exist throw exception, catch in controller and return 404
        return modelRepository.findById(integer).get();
    }
    public void insert(Model model){
        modelRepository.insert(model);
    }
    public Model trainModel(Map<String, float[]> data){
        return null;
    }
}
