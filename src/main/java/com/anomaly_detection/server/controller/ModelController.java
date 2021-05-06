package com.anomaly_detection.server.controller;

import com.anomaly_detection.server.dto.Anomaly;
import com.anomaly_detection.server.dto.ModelDto;
import com.anomaly_detection.server.exceptions.ModelNotFound;
import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "api")
@AllArgsConstructor
public class ModelController {
    private final ModelService modelService;

    @GetMapping("model/{id}")
    public @ResponseBody
    ModelDto findById(@PathVariable String id) throws ModelNotFound {
        return modelService.getById(id);
    }

    @GetMapping("models")
    public @ResponseBody
    List<ModelDto> findAllModels() throws ModelNotFound {
        return modelService.getAllModels();
    }

    @DeleteMapping("model/{id}")
    public @ResponseBody
    ModelDto deleteById(@PathVariable String id) throws ModelNotFound {
        return modelService.delete(id);
    }

    @PostMapping("model/{model_type}")
    public @ResponseBody
    ModelDto trainModel(@PathVariable String model_type, @RequestBody Map<String, ArrayList<Float>> data) {
        return modelService.trainModel(data, model_type);
    }
    
    @PostMapping("anomaly")
    public @ResponseBody
    Anomaly detect(@RequestParam String model_id, @RequestBody Map<String, ArrayList<Float>> data){
        return modelService.detect(data,model_id);
    }
}
