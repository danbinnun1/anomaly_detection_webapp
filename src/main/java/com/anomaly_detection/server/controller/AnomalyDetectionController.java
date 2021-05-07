package com.anomaly_detection.server.controller;

import com.anomaly_detection.server.dto.Anomaly;
import com.anomaly_detection.server.exceptions.InvalidDataException;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TrainingNotFinishedException;
import com.anomaly_detection.server.service.AnomalyDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "api")
public class AnomalyDetectionController {
    private final AnomalyDetectionService anomalyDetectorService;

    @Autowired
    public AnomalyDetectionController(AnomalyDetectionService anomalyDetectorService) {
        this.anomalyDetectorService = anomalyDetectorService;
    }

    @PostMapping("anomaly")
    public @ResponseBody
    Anomaly detect(@RequestParam String model_id, @RequestBody Map<String, List<Float>> data) throws TrainingNotFinishedException, InvalidDataException, ModelNotFoundException {
        return anomalyDetectorService.detect(data,model_id);
    }
}
