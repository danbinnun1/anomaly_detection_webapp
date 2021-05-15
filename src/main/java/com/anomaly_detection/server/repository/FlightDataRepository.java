package com.anomaly_detection.server.repository;

import com.anomaly_detection.server.model.FlightData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightDataRepository extends MongoRepository<FlightData, String> {
}
