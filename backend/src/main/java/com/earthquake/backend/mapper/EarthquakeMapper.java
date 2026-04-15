package com.earthquake.backend.mapper;

import com.earthquake.backend.dto.EarthquakeDto;
import com.earthquake.backend.model.Earthquake;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EarthquakeMapper {
    EarthquakeDto toDto(Earthquake earthquake);
    Earthquake toEntity(EarthquakeDto earthquakeDto);
    List<EarthquakeDto> toDtoList(List<Earthquake> earthquakes);
}
