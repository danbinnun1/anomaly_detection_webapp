package com.anomaly_detection.server.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ModelDto {
    private String modelId;
    private Date uploadTime;
    private String status;
}
