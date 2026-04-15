package com.earthquake.backend.dto;

public record EarthquakeDto(
        Double magnitude,
        String magType,
        String place,
        String title,
        Long time
) {}
