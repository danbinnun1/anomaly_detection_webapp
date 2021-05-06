package com.anomaly_detection.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason="training not finished")
public class TrainingNotFinishedException extends Exception {
}