package com.anomaly_detection.server.repository;

import com.anomaly_detection.server.model.Data;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRepository extends MongoRepository<Data, String> {
}
