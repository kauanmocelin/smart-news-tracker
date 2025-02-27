package dev.kauanmocelin.springbootrestapi.news;

import dev.kauanmocelin.springbootrestapi.appuser.AppUser;
import dev.kauanmocelin.springbootrestapi.email.EmailSender;
import dev.kauanmocelin.springbootrestapi.news.client.response.NewsApiResponse;
import dev.kauanmocelin.springbootrestapi.news.client.response.NewsArticle;
import dev.kauanmocelin.springbootrestapi.news.notification.NewsMonitorResumeEmailSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.thymeleaf.TemplateEngine;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Tests for News Monitor service")
class NewsMonitorResumeEmailSenderTest {

    @MockitoBean
    private NewsMonitorRepository newsMonitorRepository;

    @MockitoBean
    private NewsMonitorService newsMonitorService;

    @MockitoBean
    private EmailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private NewsMonitorResumeEmailSender newsMonitorResumeEmailSender;

    @Test
    @DisplayName("Should send an email when news are fetched from api")
    void shouldSendEmailWhenNewsAreFetchedFromApi() {
        var newsMonitor = new NewsMonitor();
        var appUser = new AppUser();
        appUser.setEmail("test@example.com");
        newsMonitor.setAppUser(appUser);
        newsMonitor.setKeyword("java");
        newsMonitor.setMonitoringPeriod(MonitoringPeriod.DAILY);
        final var newsApiResponse = new NewsApiResponse(
            "ok",
            1,
            List.of(new NewsArticle(
                "John Doe",  // Author
                "Breaking News: Java Takes Over the World",  // Title
                "In an unexpected turn of events, Java has become the most popular language in the world.",  // Description
                "https://example.com/java-takes-over-world",  // URL
                "https://example.com/java-image.jpg",  // URL to image
                "2025-02-27T14:30:00Z",  // Published date (ISO 8601 format)
                "Java has seen a surge in usage, making it the dominant force in programming languages. Many companies are adopting Java at a rapid pace, ensuring its future for years to come."  // Content
            ))
        );
        when(newsMonitorRepository.findAll()).thenReturn(List.of(newsMonitor));
        when(newsMonitorService.fetchNewsFromYesterday(any())).thenReturn(newsApiResponse);

        newsMonitorResumeEmailSender.sendNewsResumeEmailByPeriod(MonitoringPeriod.DAILY);

        verify(emailSender, times(1)).send(eq("test@example.com"), eq(emailTemplateProcessedByThymeleaf()), eq("Your news summary - SmartNews Tracker"));
    }

    private String emailTemplateProcessedByThymeleaf() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Your Daily News Update</title>
            </head>
            <body style="font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c;">
            
            <table role="presentation" width="100%" style="border-collapse:collapse;min-width:100%;width:100%!important" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                <tr>
                    <td width="100%" height="53" bgcolor="#0b0c0c">
                        <table role="presentation" width="100%" style="border-collapse:collapse;max-width:580px" cellpadding="0" cellspacing="0" border="0" align="center">
                            <tbody>
                            <tr>
                                <td width="70" bgcolor="#0b0c0c" valign="middle"></td>
                                <td style="font-size:28px;line-height:1.315789474;padding-left:10px">
                                    <span style="font-weight:700;color:#ffffff;text-decoration:none;">Your Daily News Update</span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
            
            <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%!important">
                <tbody>
                <tr>
                    <td height="30"><br></td>
                </tr>
                <tr>
                    <td style="font-size:19px;line-height:1.315789474;max-width:560px">
                        <p>Here are the latest news articles related to your interests:</p>
            
                        <div style="margin-bottom: 20px;">
                            <h2 style="color:#1D70B8;">Breaking News: Java Takes Over the World</h2>
                            <p><strong>Published on:</strong> <span>2025-02-27T14:30:00Z</span></p>
                            <p><strong>Author:</strong> <span>John Doe</span></p>
                            <p>In an unexpected turn of events, Java has become the most popular language in the world.</p>
                            <p><a href="https://example.com/java-takes-over-world" style="color:#1D70B8;text-decoration:none;">Read more</a></p>
                            <hr>
                        </div>
                        <p>Stay informed and have a great day!</p>
                    </td>
                </tr>
                <tr>
                    <td height="30"><br></td>
                </tr>
                </tbody>
            </table>
            
            </body>
            </html>
            """;
    }
}