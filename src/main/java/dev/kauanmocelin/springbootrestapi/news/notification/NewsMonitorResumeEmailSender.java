package dev.kauanmocelin.springbootrestapi.news.notification;

import dev.kauanmocelin.springbootrestapi.email.EmailSender;
import dev.kauanmocelin.springbootrestapi.news.MonitoringPeriod;
import dev.kauanmocelin.springbootrestapi.news.NewsMonitorRepository;
import dev.kauanmocelin.springbootrestapi.news.NewsMonitorService;
import dev.kauanmocelin.springbootrestapi.news.client.response.NewsApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class NewsMonitorResumeEmailSender {

    private final NewsMonitorRepository newsMonitorRepository;
    private final NewsMonitorService newsMonitorService;
    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    public void sendNewsResumeEmailByPeriod(final MonitoringPeriod monitoringPeriod) {
        newsMonitorRepository.findAll().forEach(newsMonitor -> {
            final NewsApiResponse newsApiResponse = newsMonitorService.fetchNewsFromYesterday(newsMonitor.getKeyword());
            if (newsMonitor.getMonitoringPeriod() == monitoringPeriod) {
                emailSender.send(newsMonitor.getAppUser().getEmail(), buildNewsEmail(newsApiResponse), "Your news summary - SmartNews Tracker");
            }
        });
    }

    private String buildNewsEmail(NewsApiResponse newsApiResponse) {
        Context context = new Context();
        context.setVariable("articles", newsApiResponse.newsArticleResponse());
        return templateEngine.process("news-summary-email", context);
    }
}
