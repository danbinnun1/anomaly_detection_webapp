package com.anomaly_detection.server.controller;

import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping(path = "api/model")
@AllArgsConstructor
public class ModelController {
    private final ModelService modelService;

    @GetMapping("/{id}")
    public @ResponseBody
    Model findById(@PathVariable String id) {
        return modelService.getById(id);
    }

    @PostMapping("/{model_type}")
    public @ResponseBody
    ModelDto trainModel(@PathVariable String model_type, @RequestBody Map<String, ArrayList<Float>> data) {
        return modelService.trainModel(data, model_type);
    }

}
