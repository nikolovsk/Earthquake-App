package com.earthquake.backend.specification;

import com.earthquake.backend.model.Earthquake;
import org.springframework.data.jpa.domain.Specification;

public class EarthquakeSpecification {
    public static Specification<Earthquake> hasMinMagnitude(Double magnitude) {
        return (root, query, cb) ->
                magnitude == null ? null : cb.greaterThan(root.get("magnitude"), magnitude);
    }

    public static Specification<Earthquake> hasTimeAfter(Long time) {
        return (root, query, cb) ->
                time == null ? null : cb.greaterThan(root.get("time"), time);
    }
}
