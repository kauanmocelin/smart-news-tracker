package dev.kauanmocelin.springbootrestapi.news.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NewsApiResponse(
    String status,
    int totalResults,
    @JsonProperty("articles")
    List<NewsArticle> newsArticleRespons
) {
}

