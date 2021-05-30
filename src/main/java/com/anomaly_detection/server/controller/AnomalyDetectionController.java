package com.anomaly_detection.server.controller;

import com.anomaly_detection.server.dto.Anomaly;
import com.anomaly_detection.server.exceptions.InvalidDataException;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TrainingNotFinishedException;
import com.anomaly_detection.server.service.IAnomalyDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "api")
public class AnomalyDetectionController {
    private final IAnomalyDetectionService anomalyDetectorService;

    @Autowired
    public AnomalyDetectionController(IAnomalyDetectionService anomalyDetectorService) {
        this.anomalyDetectorService = anomalyDetectorService;
    }

    @PostMapping("anomaly")
    public @ResponseBody
    Anomaly detect(@RequestParam("model_id") String model_id, @RequestBody Map<String, List<Float>> data) throws TrainingNotFinishedException, InvalidDataException, ModelNotFoundException {
        return anomalyDetectorService.detect(data,model_id);
    }
}
