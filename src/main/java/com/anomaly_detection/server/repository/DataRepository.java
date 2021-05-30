package com.anomaly_detection.server.repository;

import com.anomaly_detection.server.model.TrainingData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRepository extends MongoRepository<TrainingData, String> {
}
