package com.earthquake.backend.service;

import com.earthquake.backend.dto.EarthquakeDto;

import java.util.List;

public interface EarthquakeService {
    List<EarthquakeDto> getAll();

    void refreshEarthquakes();

    List<EarthquakeDto> filter(Double magnitude, Long time);
}
