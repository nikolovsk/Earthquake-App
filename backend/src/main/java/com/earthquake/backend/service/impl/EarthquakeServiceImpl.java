package com.earthquake.backend.service.impl;

import com.earthquake.backend.client.EarthquakeApiClient;
import com.earthquake.backend.dto.EarthquakeDto;
import com.earthquake.backend.dto.api.EarthquakeApiResponse;
import com.earthquake.backend.dto.api.Feature;
import com.earthquake.backend.exception.EarthquakeNotFoundException;
import com.earthquake.backend.mapper.EarthquakeMapper;
import com.earthquake.backend.model.Earthquake;
import com.earthquake.backend.repository.EarthquakeJpaRepository;
import com.earthquake.backend.service.EarthquakeService;
import com.earthquake.backend.specification.EarthquakeSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void refreshEarthquakes() {
        EarthquakeApiResponse response = apiClient.fetchEarthquakeData();

        earthquakeRepository.deleteAll();

        List<Earthquake> earthquakes = response.features()
                .stream()
                .map(Feature::properties)
                .filter(p -> p.magnitude() != null)
                .map(mapper::toEntity)
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
