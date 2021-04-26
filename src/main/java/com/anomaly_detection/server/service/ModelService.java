package com.anomaly_detection.server.service;

import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ModelService {
    private final ModelRepository modelRepository;
    public Model getById(Integer integer){
        //implement: if model doesn't exist throw exception, catch in controller and return 404
        return modelRepository.findById(integer).get();
    }
    public void insert(Model model){
        modelRepository.insert(model);
    }
}
