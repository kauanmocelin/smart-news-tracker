package dev.kauanmocelin.springbootrestapi.news;

import dev.kauanmocelin.springbootrestapi.appuser.AuthenticatedUserService;
import dev.kauanmocelin.springbootrestapi.appuser.mapper.AppUserMapper;
import dev.kauanmocelin.springbootrestapi.news.request.KeywordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsMonitorService {

    private final NewsMonitorRepository newsMonitorRepository;
    private final AppUserMapper appUserMapper;
    private final AuthenticatedUserService AuthenticatedUserService;

    public String register(KeywordRequest keyword) {
        newsMonitorRepository.save(NewsMonitor.builder()
                .keyword(keyword.keyword())
                .monitoringPeriod(keyword.monitoringPeriod())
                .appUser(AuthenticatedUserService.getLoggedInUser())
            .build());
        return null;
    }
}
