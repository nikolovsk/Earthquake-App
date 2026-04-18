package com.earthquake.backend.dto.api;

import java.util.List;

public record Geometry(
        List<Double> coordinates
) {}
