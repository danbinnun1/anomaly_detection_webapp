package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.dto.ModelMapper;
import com.anomaly_detection.server.exceptions.TypeNotSupportedException;
import com.anomaly_detection.server.model.Data;
import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.repository.DataRepository;
import com.anomaly_detection.server.repository.ModelRepository;
import com.anomaly_detection.server.service.algorithms.TimeSeries;
import com.anomaly_detection.server.service.algorithms.TimeSeriesAnomalyDetector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Service
public class ConcreteModelTrainingService implements ModelTrainingService {
    private final ModelRepository modelRepository;
    private final DataRepository dataRepository;

    private final AnomalyDetectorFactory anomalyDetectorFactory = new AnomalyDetectorFactory();
    private final ExecutorService executorService = Executors.newFixedThreadPool(threadPoolBacklog);

    private static final int threadPoolBacklog = 20;

    @Override
    public ModelDto trainModel(Map<String, List<Float>> data, String type) throws TypeNotSupportedException {
        TimeSeriesAnomalyDetector anomalyDetector = anomalyDetectorFactory.createAnomalyDetector(type);

        if (anomalyDetector == null) {
            throw new TypeNotSupportedException();
        }

        var model = new Model();
        modelRepository.save(model);

        var flightData = new Data().setModel(model).setFlightData(data).setModelType(type);
        dataRepository.save(flightData);

        executorService.execute(() -> {
            //train model and update database
            anomalyDetector.learnNormal(new TimeSeries(data));

            model.setColumnsNames(data.keySet()).setDetector(anomalyDetector).setStatus("ready");
            modelRepository.save(model);

            dataRepository.delete(flightData);

        });

        return ModelMapper.toModelDto(model);
    }

    @PostConstruct
    public void init() {
        var flightDataList = dataRepository.findAll();

        for (var flightData : flightDataList) {
            TimeSeriesAnomalyDetector anomalyDetector = anomalyDetectorFactory.createAnomalyDetector(flightData.getModelType());

            executorService.execute(() -> {
                var model = flightData.getModel();
                var data = flightData.getFlightData();

                //train model and update database
                anomalyDetector.learnNormal(new TimeSeries(data));

                model.setColumnsNames(data.keySet()).setDetector(anomalyDetector).setStatus("ready");
                modelRepository.save(model);

                dataRepository.delete(flightData);
            });
        }
    }
}
