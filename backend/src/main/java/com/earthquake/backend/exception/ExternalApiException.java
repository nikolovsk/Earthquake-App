package com.earthquake.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ExternalApiException extends RuntimeException {
    public ExternalApiException() {
        super("Failed to fetch earthquake data from USGS API");
    }
}
