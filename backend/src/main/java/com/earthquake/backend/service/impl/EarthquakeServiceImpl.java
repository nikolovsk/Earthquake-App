package com.earthquake.backend.service.impl;

import com.earthquake.backend.client.EarthquakeApiClient;
import com.earthquake.backend.dto.EarthquakeDto;
import com.earthquake.backend.dto.api.EarthquakeApiResponse;
import com.earthquake.backend.dto.api.Geometry;
import com.earthquake.backend.dto.api.Properties;
import com.earthquake.backend.exception.EarthquakeNotFoundException;
import com.earthquake.backend.exception.ExternalApiException;
import com.earthquake.backend.mapper.EarthquakeMapper;
import com.earthquake.backend.model.Earthquake;
import com.earthquake.backend.repository.EarthquakeJpaRepository;
import com.earthquake.backend.service.EarthquakeService;
import com.earthquake.backend.specification.EarthquakeSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class EarthquakeServiceImpl implements EarthquakeService {

    private final EarthquakeJpaRepository earthquakeRepository;
    private final EarthquakeMapper mapper;
    private final EarthquakeApiClient apiClient;

    public EarthquakeServiceImpl(EarthquakeJpaRepository earthquakeRepository, EarthquakeMapper mapper,
                                 EarthquakeApiClient apiClient) {
        this.earthquakeRepository = earthquakeRepository;
        this.mapper = mapper;
        this.apiClient = apiClient;
    }

    @Override
    public List<EarthquakeDto> getAll() {
        List<Earthquake> earthquakes = earthquakeRepository.findAll();
        return mapper.toDtoList(earthquakes);
    }

    @Override
    @Transactional
    public synchronized void refreshEarthquakes() {
        EarthquakeApiResponse response = apiClient.fetchEarthquakeData();

        if (response == null || response.features() == null) {
            throw new ExternalApiException();
        }

        earthquakeRepository.deleteAll();
        earthquakeRepository.flush();

        List<Earthquake> earthquakes = response.features()
                .stream()
                .filter(Objects::nonNull)
                .map(feature -> {
                    Properties properties = feature.properties();
                    Geometry geometry = feature.geometry();

                    if (properties == null ||
                            properties.magnitude() == null ||
                            properties.magType() == null ||
                            properties.place() == null ||
                            properties.title() == null ||
                            properties.time() == null ||
                            geometry == null ||
                            geometry.coordinates() == null ||
                            geometry.coordinates().size() < 2) {
                        return null;
                    }

                    Earthquake earthquake = mapper.toEntity(properties);

                    List<Double> coordinates = geometry.coordinates();

                    earthquake.setLongitude(coordinates.get(0));
                    earthquake.setLatitude(coordinates.get(1));

                    return earthquake;
                })
                .filter(Objects::nonNull)
                .toList();

        earthquakeRepository.saveAll(earthquakes);
    }

    @Override
    public List<EarthquakeDto> filter(Double magnitude, Long time) {
        Specification<Earthquake> spec = Specification
                .where(EarthquakeSpecification.hasMinMagnitude(magnitude))
                .and(EarthquakeSpecification.hasTimeAfter(time));

        List<Earthquake> earthquakes = earthquakeRepository.findAll(spec);

        return mapper.toDtoList(earthquakes);
    }

    @Override
    public void deleteById(Long id) {
        Earthquake earthquake = earthquakeRepository.findById(id)
                .orElseThrow(() -> new EarthquakeNotFoundException(id));

        earthquakeRepository.delete(earthquake);
    }
}
