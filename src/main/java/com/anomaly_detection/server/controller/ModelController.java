package com.anomaly_detection.server.controller;

import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TypeNotSupportedException;
import com.anomaly_detection.server.service.IModelService;
import com.anomaly_detection.server.service.IModelTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "api")
public class ModelController {
    private final IModelService modelService;
    private final IModelTrainingService modelTrainingService;

    @Autowired
    public ModelController(IModelService modelService, IModelTrainingService modelTrainingService) {
        this.modelService = modelService;
        this.modelTrainingService = modelTrainingService;
    }

    @GetMapping("model")
    public @ResponseBody
    ModelDto findById(@RequestParam("model_id") String id) throws ModelNotFoundException {
        return modelService.getById(id);
    }

    @GetMapping("models")
    public @ResponseBody
    List<ModelDto> findAllModels() {
        return modelService.getAllModels();
    }

    @DeleteMapping("model")
    public @ResponseBody
    ModelDto deleteById(@RequestParam("model_id") String id) throws ModelNotFoundException {
        return modelService.delete(id);
    }

    @PostMapping("model")
    public @ResponseBody
    ModelDto trainModel(@RequestParam("model_type") String model_type, @RequestBody Map<String, List<Float>> data) throws TypeNotSupportedException {
        return modelTrainingService.trainModel(data, model_type);
    }
}
