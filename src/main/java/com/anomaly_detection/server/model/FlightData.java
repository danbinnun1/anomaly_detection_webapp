package com.anomaly_detection.server.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@Document
public class FlightData {
    @Id
    public String id;
    @DBRef
    private Model model;

    private Map<String, List<Float>> flightData;
    private String modelType;
}
