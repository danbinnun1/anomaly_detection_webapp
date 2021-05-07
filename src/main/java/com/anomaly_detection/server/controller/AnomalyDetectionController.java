package com.anomaly_detection.server.controller;

import com.anomaly_detection.server.dto.Anomaly;
import com.anomaly_detection.server.exceptions.InvalidDataException;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TrainingNotFinishedException;
import com.anomaly_detection.server.service.AnomalyDetectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "api")
@AllArgsConstructor
public class AnomalyDetectionController {
    private final AnomalyDetectionService anomalyDetectorService;

    @PostMapping("anomaly")
    public @ResponseBody
    Anomaly detect(@RequestParam String model_id, @RequestBody Map<String, List<Float>> data) throws TrainingNotFinishedException, InvalidDataException, ModelNotFoundException {
        return anomalyDetectorService.detect(data,model_id);
    }
}
