package com.earthquake.backend.dto.api;

import java.util.List;

public record EarthquakeApiResponse(
        List<Feature> features
) {}
