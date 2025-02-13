package dev.kauanmocelin.springbootrestapi.news;

import dev.kauanmocelin.springbootrestapi.news.request.KeywordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news-monitor")
@RequiredArgsConstructor
public class NewsMonitorController {

    private final NewsMonitorService newsMonitorService;

    @PostMapping("/keyword")
    public ResponseEntity<String> registerKeyword(@RequestBody final KeywordRequest request) {
        return ResponseEntity.ok(newsMonitorService.register(request));
    }
}
