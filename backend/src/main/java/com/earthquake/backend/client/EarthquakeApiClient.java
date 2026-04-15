package com.earthquake.backend.client;

import com.earthquake.backend.dto.api.EarthquakeApiResponse;
import com.earthquake.backend.exception.ExternalApiException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EarthquakeApiClient {

    private final WebClient webClient;

    private static final String url = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

    public EarthquakeApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public EarthquakeApiResponse fetchEarthquakeData() {
        try {
            return webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(EarthquakeApiResponse.class)
                    .block();
        } catch (Exception ex) {
            throw new ExternalApiException();
        }
    }
}
