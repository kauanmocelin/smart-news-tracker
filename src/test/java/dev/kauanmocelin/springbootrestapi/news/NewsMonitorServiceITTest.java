package dev.kauanmocelin.springbootrestapi.news;

import dev.kauanmocelin.springbootrestapi.appuser.AuthenticatedUserService;
import dev.kauanmocelin.springbootrestapi.news.dto.KeywordRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@DisplayName("Tests for News Monitor service")
class NewsMonitorServiceITTest {

    @Autowired
    private NewsMonitorService newsMonitorService;

    @MockitoBean
    private NewsMonitorRepository newsMonitorRepository;

    @MockitoBean
    private AuthenticatedUserService authenticatedUserService;

    @Container
    static WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:2.35.1-1")
        .withMappingFromResource("wiremock-mappings/news-api-response.json");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("news.api.url", wiremockServer::getBaseUrl);
    }

    @Test
    void shouldFetchThreeNewsArticlesWhenJavaKeywordIsProvided() {
        final var keyword = "java";

        final var fetchedNews = newsMonitorService.fetchNewsFromYesterday(keyword);

        assertThat(fetchedNews).isNotNull();
        assertThat(fetchedNews.status()).isEqualTo("ok");
        assertThat(fetchedNews.totalResults()).isEqualTo(3);
        assertThat(fetchedNews.newsArticleResponse())
            .hasSize(3);
    }

    @Test
    void shouldRegisterNewKeywordWhenValidRequestIsProvided() {
        var keywordRequest = new KeywordRequest("java", MonitoringPeriod.DAILY);

        newsMonitorService.register(keywordRequest);

        verify(newsMonitorRepository, times(1)).save(any(NewsMonitor.class));
    }
}