package com.anomaly_detection.server.repository;

import com.anomaly_detection.server.model.Model;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModelRepository extends MongoRepository<Model, Integer> {
}
