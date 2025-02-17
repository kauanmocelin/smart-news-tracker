package dev.kauanmocelin.springbootrestapi.news;

import dev.kauanmocelin.springbootrestapi.appuser.AuthenticatedUserService;
import dev.kauanmocelin.springbootrestapi.news.client.NewsApiClient;
import dev.kauanmocelin.springbootrestapi.news.client.response.NewsApiResponse;
import dev.kauanmocelin.springbootrestapi.news.request.KeywordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsMonitorService {

    private final NewsMonitorRepository newsMonitorRepository;
    private final NewsApiClient newsApiClient;
    private final AuthenticatedUserService authenticatedUserService;

    public void register(KeywordRequest keyword) {
        newsMonitorRepository.save(NewsMonitor.builder()
            .keyword(keyword.keyword())
            .monitoringPeriod(keyword.monitoringPeriod())
            .appUser(authenticatedUserService.getLoggedInUser())
            .build());
    }

    public NewsApiResponse fetchNewsFromYesterday(final String keyword) {
        return newsApiClient.getNewsFromYesterday(keyword);
    }
}
