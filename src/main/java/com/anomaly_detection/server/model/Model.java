package com.anomaly_detection.server.model;

import com.anomaly_detection.server.service.algorithms.TimeSeriesAnomalyDetector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Document
public class Model {
    @Id
    private String id;
    private Date insertionTime;
    private String status;
    private TimeSeriesAnomalyDetector detector;
    private Set<String> columnsNames;

    public Model() {
        insertionTime = new Date();
        status = "pending";
    }

    public Model(Model other) {
        this.id = other.id;
        this.insertionTime = other.insertionTime;
        this.status = other.status;
        this.detector = other.detector;
        this.columnsNames = other.columnsNames;
    }
}
