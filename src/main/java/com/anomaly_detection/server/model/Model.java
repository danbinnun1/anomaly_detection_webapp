package com.anomaly_detection.server.model;

import com.anomaly_detection.server.service.algorithms.TimeSeriesAnomalyDetector;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Data
@Accessors(chain = true)
@Document
public class Model {
    @Id
    private String id;
    private Date uploadTime;
    private String status;
    private TimeSeriesAnomalyDetector detector;
    private Set<String> columnsNames;

    public Model() {
        uploadTime = new Date();
        status = "pending";
    }

    public Model(Model other) {
        this.id = other.id;
        this.uploadTime = other.uploadTime;
        this.status = other.status;
        this.detector = other.detector;
        this.columnsNames = other.columnsNames;
    }
}
