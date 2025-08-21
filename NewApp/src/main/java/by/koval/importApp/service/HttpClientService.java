package by.koval.importApp.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class HttpClientService {
    private final RestTemplate restTemplate;

    public HttpClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(60))
                .build();
    }

    public <T> ResponseEntity<T> postRequest(String url, Class<T> responseType) {
        try {
            return restTemplate.getForEntity(url, responseType);
        } catch (Exception e) {
            throw new RuntimeException("Error getting request", e);
        }
    }

    public <T, R> ResponseEntity<R> postRequest(String url, T requestBody, Class<R> responseType) {
        try {
            return restTemplate.postForEntity(url, requestBody, responseType);
        } catch (Exception e) {
            throw new RuntimeException("Error posting request", e);
        }
    }
}
