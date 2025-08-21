package by.koval.importApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

@Service
public class HttpClientService {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(HttpClientService.class);

    public HttpClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(60))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();
    }

    public <T, R> ResponseEntity<R> postRequest(String url, T requestBody, Class<R> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<T> entity = new HttpEntity<>(requestBody, headers);

            return restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    responseType
            );
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error при выполнении запроса: {}", e.getResponseBodyAsString());
            throw new RuntimeException("HTTP ошибка при выполнении запроса", e);
        } catch (ResourceAccessException e) {
            logger.error("Сетевая ошибка при выполнении запроса: {}", e.getMessage());
            throw new RuntimeException("Сетевая ошибка при выполнении запроса", e);
        } catch (Exception e) {
            logger.error("Ошибка при выполнении запроса: {}", e.getMessage());
            throw new RuntimeException("Ошибка при выполнении запроса", e);
        }
    }
}
