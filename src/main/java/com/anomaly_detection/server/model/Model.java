package com.anomaly_detection.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@Document
public class Model {
    @Id
    private Integer id;
    private Date insertionTime;
    private String status;
}
