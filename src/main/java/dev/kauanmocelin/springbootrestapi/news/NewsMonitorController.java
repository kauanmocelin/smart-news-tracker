package dev.kauanmocelin.springbootrestapi.news;

import dev.kauanmocelin.springbootrestapi.news.client.response.NewsApiResponse;
import dev.kauanmocelin.springbootrestapi.news.request.KeywordRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news-monitor")
@RequiredArgsConstructor
public class NewsMonitorController {

    private final NewsMonitorService newsMonitorService;

    @PostMapping("/keyword")
    public ResponseEntity<Void> registerKeyword(@RequestBody @Valid final KeywordRequest request) {
        newsMonitorService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/news/{keyword}")
    public ResponseEntity<NewsApiResponse> fetchNews(@PathVariable String keyword) {
        return ResponseEntity.ok(newsMonitorService.fetchNews(keyword));
    }
}
