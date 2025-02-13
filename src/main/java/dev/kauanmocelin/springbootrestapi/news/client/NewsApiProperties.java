package dev.kauanmocelin.springbootrestapi.news.client;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "news.api")
@Validated
@Getter
@Setter
public class NewsApiProperties {

    @NotBlank(message = "news.api.url cannot be empty")
    private String url;

    @NotBlank(message = "news.api.apiKey cannot be empty")
    private String apiKey;
}
