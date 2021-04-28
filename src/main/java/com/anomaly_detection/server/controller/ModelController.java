package com.anomaly_detection.server.controller;

import com.anomaly_detection.server.model.Model;
import com.anomaly_detection.server.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path = "api/model")
@AllArgsConstructor
public class ModelController {
    private final ModelService modelService;
    @GetMapping("/{id}")
    public @ResponseBody Model findById(@PathVariable Integer id){
        return modelService.getById(id);
    }
    @PostMapping("/{model_type}")
    public @ResponseBody Model trainModel(@RequestBody Map<String, float[]> data){
        return null;
    }
}
