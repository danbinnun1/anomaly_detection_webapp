package com.anomaly_detection.server.controller;

import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.ModelNotFoundException;
import com.anomaly_detection.server.exceptions.TypeNotSupportedException;
import com.anomaly_detection.server.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "api")
public class ModelController {
    private final ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("model/{id}")
    public @ResponseBody
    ModelDto findById(@PathVariable String id) throws ModelNotFoundException {
        return modelService.getById(id);
    }

    @GetMapping("models")
    public @ResponseBody
    List<ModelDto> findAllModels() {
        return modelService.getAllModels();
    }

    @DeleteMapping("model/{id}")
    public @ResponseBody
    ModelDto deleteById(@PathVariable String id) throws ModelNotFoundException {
        return modelService.delete(id);
    }

    @PostMapping("model/{model_type}")
    public @ResponseBody
    ModelDto trainModel(@PathVariable String model_type, @RequestBody Map<String, List<Float>> data) throws TypeNotSupportedException {
        return modelService.trainModel(data, model_type);
    }
}
