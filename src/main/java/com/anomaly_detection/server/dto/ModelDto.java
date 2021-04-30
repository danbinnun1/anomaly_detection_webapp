package com.anomaly_detection.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class ModelDto {
    private String model_id;
    private Date upload_time;
    private String status;
}
