package dev.kauanmocelin.springbootrestapi.news.client;

import dev.kauanmocelin.springbootrestapi.news.client.response.NewsApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class NewsApiClient {

    private final RestTemplate restTemplate;
    private final NewsApiProperties properties;

    public NewsApiResponse getNewsFromYesterday(final String keyword) {
        final var apiUrl = UriComponentsBuilder
            .fromUriString(properties.getUrl())
            .path("/everything")
            .queryParam("q", keyword)
            .queryParam("from", LocalDate.now().minusDays(1) .toString())
            .queryParam("sortBy", "popularity")
            .queryParam("pageSize", "10")
            .queryParam("language", "en")
            .queryParam("apiKey", properties.getApiKey())
            .toUriString();
        return restTemplate.getForObject(apiUrl, NewsApiResponse.class);
    }
}
