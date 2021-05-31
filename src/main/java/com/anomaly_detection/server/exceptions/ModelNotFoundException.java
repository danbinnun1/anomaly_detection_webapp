package com.anomaly_detection.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason="no such model exists")
public class ModelNotFoundException extends Exception {
}
