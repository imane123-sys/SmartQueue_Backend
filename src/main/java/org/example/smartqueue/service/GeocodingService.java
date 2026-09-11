package org.example.smartqueue.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

    public class GeocodingService {

        private final RestTemplate restTemplate;

        @Value("${google.maps.api-key}")
        private String apiKey;

        public double[] getCoordinates(String adresse) {

            String url = "https://maps.googleapis.com/maps/api/geocode/json"
                    + "?address=" + adresse
                    + "&key=" + apiKey;

            Map result = restTemplate.getForObject(url, Map.class);

            List results = (List) result.get("results");

            if (results.isEmpty()) {
                throw new RuntimeException("Adresse introuvable");
            }

            Map geometry = (Map) ((Map) results.get(0)).get("geometry");
            Map location = (Map) geometry.get("location");

            double latitude = (double) location.get("lat");
            double longitude = (double) location.get("lng");

            return new double[]{latitude, longitude};
        }
    }





