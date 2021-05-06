package com.anomaly_detection.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason="invalid training algorithm type")
public class TypeNotSupportedException extends Exception {
}