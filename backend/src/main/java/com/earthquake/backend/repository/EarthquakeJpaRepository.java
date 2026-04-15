package com.earthquake.backend.repository;

import com.earthquake.backend.model.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarthquakeJpaRepository extends JpaRepository<Earthquake, Long> {
}
