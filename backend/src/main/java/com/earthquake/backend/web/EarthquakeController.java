package com.earthquake.backend.web;

import com.earthquake.backend.dto.EarthquakeDto;
import com.earthquake.backend.service.EarthquakeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/earthquakes")
public class EarthquakeController {
    private final EarthquakeService earthquakeService;

    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    @GetMapping
    public ResponseEntity<List<EarthquakeDto>> getAll() {
        return ResponseEntity.ok(earthquakeService.getAll());
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh() {
        earthquakeService.refreshEarthquakes();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EarthquakeDto>> filter(
            @RequestParam(required = false) Double magnitude,
            @RequestParam(required = false) Long time) {
        return ResponseEntity.ok(earthquakeService.filter(magnitude, time));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        earthquakeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
