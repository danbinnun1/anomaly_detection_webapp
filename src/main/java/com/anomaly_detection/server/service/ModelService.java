package com.anomaly_detection.server.service;

import com.anomaly_detection.server.dto.Anomaly;
import com.anomaly_detection.server.dto.Span;
import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.InvalidDataException;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TrainingNotFinishedException;
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
    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    public ModelDto getById(String integer) throws ModelNotFoundException {
        Optional<Model> model=modelRepository.findById(integer);
        if (model.isEmpty()){
            throw new ModelNotFoundException();
        }
        return modelToDto(model.get());
    }

    public List<ModelDto> getAllModels() {
        List<Model> models = modelRepository.findAll();
        List<ModelDto> dtos = new ArrayList<>();
        for(Model m : models){
            dtos.add(modelToDto(m));
        }
        return dtos;
    }

    public void insert(Model model) {
        modelRepository.insert(model);
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
            model.setColumnsNames(data.keySet());
            finalDetector.learnNormal(new TimeSeries(data));
            model.setDetector(finalDetector);
            model.setStatus("ready");
            modelRepository.save(model);
        });
        return modelToDto(model);
    }

    public Anomaly detect(Map<String, ArrayList<Float>> data, String modelId) throws InvalidDataException, TrainingNotFinishedException {
        Model model = modelRepository.findById(modelId).get();
        if(model.getStatus().equals("pending")){
            throw new TrainingNotFinishedException();
        }
        //data must contain all columns the model trained on
        if (!data.keySet().containsAll(model.getColumnsNames())) {
            throw new InvalidDataException();
        }
        //if data contains additional columns the models didn't trained on, they can be ignored
        for (String key : data.keySet()) {
            if (!model.getColumnsNames().contains(key)) {
                data.remove(key);
            }
        }
        ArrayList<AnomalyReport> anomalies = model.getDetector().detect(new TimeSeries(data));
        //get Anomaly object made of spans
        return getAnomaly(anomalies, model.getColumnsNames());
    }

    private Anomaly getAnomaly(ArrayList<AnomalyReport> anomalies, Set<String> columnNames){
        Map<String, List<Span>> result = new TreeMap<>();
        for (String column : columnNames) {
            List<Integer> columnAnomalies = new ArrayList<>();
            for (AnomalyReport anomalyReport : anomalies) {
                if (column.equals(anomalyReport.getFeature1()) || column
                        .equals(anomalyReport.getFeature2())) {
                    columnAnomalies.add(anomalyReport.getTimeStep());
                }
            }
            result.put(column, getSpans(columnAnomalies));
        }
        return new Anomaly(result, "");
    }

    private List<Span> getSpans(List<Integer> integers) {
        Set<Integer> set=new LinkedHashSet<>(integers);
        integers=new ArrayList<>(set);
        Collections.sort(integers);
        List<Span> result = new ArrayList<>();
        int lastOfSequence = -1;
        int firstOfSequence = -1;
        for (int i : integers) {
            if (firstOfSequence == -1) {
                firstOfSequence = i;
                lastOfSequence = i;
            } else if (i == lastOfSequence + 1) {
                lastOfSequence++;
            } else {
                result.add(new Span(firstOfSequence, lastOfSequence + 1));
                firstOfSequence = i;
                lastOfSequence = i;
            }
        }
        if (firstOfSequence!=-1){
            result.add(new Span(firstOfSequence, lastOfSequence + 1));
        }
        return result;
    }

    public ModelDto delete(String modelId) throws ModelNotFoundException {
        Optional<Model> model = modelRepository.findById(modelId);
        modelRepository.deleteById(modelId);
        if (model.isEmpty()){
            throw new ModelNotFoundException();
        }
        return modelToDto(model.get());
    }

    private ModelDto modelToDto(Model model){
        return new ModelDto(model.getId(), model.getInsertionTime(), model.getStatus());
    }

}
