package com.earthquake.backend.service;

import com.earthquake.backend.client.EarthquakeApiClient;
import com.earthquake.backend.dto.api.EarthquakeApiResponse;
import com.earthquake.backend.dto.api.Feature;
import com.earthquake.backend.dto.api.Properties;
import com.earthquake.backend.mapper.EarthquakeMapper;
import com.earthquake.backend.model.Earthquake;
import com.earthquake.backend.repository.EarthquakeJpaRepository;
import com.earthquake.backend.service.impl.EarthquakeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EarthquakeServiceTest {

    @Mock
    private EarthquakeJpaRepository repository;

    @Mock
    private EarthquakeApiClient apiClient;

    @Mock
    private EarthquakeMapper mapper;

    @InjectMocks
    private EarthquakeServiceImpl earthquakeService;

    @Test
    void refreshAndFilterEarthquakes() {

        Feature f1 = new Feature(new Properties(1.5, "ML", "A", "T", 1000L));
        Feature f2 = new Feature(new Properties(3.0, "ML", "B", "T", 2000L));

        when(apiClient.fetchEarthquakeData())
                .thenReturn(new EarthquakeApiResponse(List.of(f1, f2)));

        earthquakeService.refreshEarthquakes();

        verify(repository).deleteAllInBatch();
        verify(repository).saveAll(anyList());
    }

    @Test
    void deleteEarthquakeById() {

        Earthquake e = new Earthquake(1L, 4.0, "ML", "Place", "Title", 1000L);

        when(repository.findById(1L)).thenReturn(Optional.of(e));

        earthquakeService.deleteById(1L);

        verify(repository).delete(e);
    }

    @Test
    void filterEarthquakesByTimeAndMagnitude() {

        Earthquake earthquake = new Earthquake(2L, 5.0, "ML", "Tokyo", "Title", 2000L);

        when(repository.findAll(any(Specification.class)))
                .thenReturn(List.of(earthquake));

        when(mapper.toDtoList(anyList()))
                .thenReturn(List.of(
                        new com.earthquake.backend.dto.EarthquakeDto(5.0, "ML", "Tokyo", "Title", 2000L)
                ));

        var result = earthquakeService.filter(2.0, 1500L);

        verify(repository).findAll(any(Specification.class));

        assertEquals(1, result.size());
        assertEquals("Tokyo", result.get(0).place());
    }
}