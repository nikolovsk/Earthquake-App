package com.earthquake.backend.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Properties(
        @JsonProperty("mag")
        Double magnitude,

        String magType,

        String place,

        String title,

        Long time
) {}
