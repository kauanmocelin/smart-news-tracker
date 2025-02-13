package dev.kauanmocelin.springbootrestapi.news.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewsArticle(
    @JsonProperty("source")
    NewsSource newsSource,
    String author,
    String title,
    String description,
    String url,
    String urlToImage,
    String publishedAt,
    String content
) {
}
