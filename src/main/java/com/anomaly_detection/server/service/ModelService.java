package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.Anomaly;
import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.repository.ModelRepository;
import com.anomaly_detection.server.service.algorithms.HybridAnomalyDetector;
import com.anomaly_detection.server.service.algorithms.SimpleAnomalyDetector;
import com.anomaly_detection.server.service.algorithms.TimeSeries;
import com.anomaly_detection.server.service.algorithms.TimeSeriesAnomalyDetector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Service
public class ModelService {
    private final ModelRepository modelRepository;
    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    public Model getById(String integer) {
        //implement: if model doesn't exist throw exception, catch in controller and return 404
        return modelRepository.findById(integer).get();
    }

    public void insert(Model model) {
        modelRepository.insert(model);
    }

    public ModelDto trainModel(Map<String, ArrayList<Float>> data, String type) {
        TimeSeriesAnomalyDetector detector = null;
        if (type.equals("regression")) {
            detector = new SimpleAnomalyDetector();
        } else if (type.equals("hybrid")) {
            detector = new HybridAnomalyDetector();
        } else {

        }
        Model model = new Model();
        modelRepository.save(model);
        TimeSeriesAnomalyDetector finalDetector = detector;
        Model modelCopy=new Model(model);
        executorService.execute(() -> {
            model.setColumnsNames(data.keySet());
            finalDetector.learnNormal(new TimeSeries(data));
            model.setDetector(finalDetector);
            model.setStatus("ready");
            modelRepository.save(model);
        });
        return new ModelDto(model.getId(),model.getInsertionTime(),model.getStatus());
    }
    public Anomaly detect(Map<String, ArrayList<Float>> data, String modelId){
        Model model=modelRepository.findById(modelId).get();
        return null;
    }
}
