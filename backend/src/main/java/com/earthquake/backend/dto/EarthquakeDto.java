package com.earthquake.backend.dto;

public record EarthquakeDto(
        Long id,
        Double magnitude,
        String magType,
        String place,
        String title,
        Long time,
        Double latitude,
        Double longitude
) {}
