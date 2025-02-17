package dev.kauanmocelin.springbootrestapi.news.client.response;

public record NewsArticle(
    String author,
    String title,
    String description,
    String url,
    String urlToImage,
    String publishedAt,
    String content
) {
}
