package dev.kauanmocelin.springbootrestapi.news.notification;

import dev.kauanmocelin.springbootrestapi.email.EmailService;
import dev.kauanmocelin.springbootrestapi.news.MonitoringPeriod;
import dev.kauanmocelin.springbootrestapi.news.NewsMonitorRepository;
import dev.kauanmocelin.springbootrestapi.news.NewsMonitorService;
import dev.kauanmocelin.springbootrestapi.news.client.response.NewsApiResponse;
import dev.kauanmocelin.springbootrestapi.news.client.response.NewsArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsMonitorResumeEmailSender {

    private final NewsMonitorRepository newsMonitorRepository;
    private final NewsMonitorService newsMonitorService;
    private final EmailService emailService;

    public void sendNewsResumeEmailByPeriod(final MonitoringPeriod monitoringPeriod) {
        newsMonitorRepository.findAll().forEach(newsMonitor -> {
            final NewsApiResponse newsApiResponse = newsMonitorService.fetchNewsFromYesterday(newsMonitor.getKeyword());
            if (newsMonitor.getMonitoringPeriod() == monitoringPeriod) {
                emailService.send(newsMonitor.getAppUser().getEmail(), buildNewsEmail(newsApiResponse));
            }
        });
    }

    private String buildNewsEmail(NewsApiResponse newsApiResponse) {
        StringBuilder newsContent = new StringBuilder();

        for (NewsArticle article : newsApiResponse.newsArticleResponse()) {
            newsContent.append("<div style=\"margin-bottom: 20px;\">")
                .append("<h2 style=\"color:#1D70B8;\">" + article.title() + "</h2>")
                .append("<p><strong>Published on:</strong> " + (article.publishedAt() != null ? article.publishedAt() : "Unknown date") + "</p>")
                .append("<p><strong>Author:</strong> " + (article.author() != null ? article.author() : "Unknown") + "</p>")
                .append("<p>" + (article.description() != null ? article.description() : "No description available.") + "</p>")
                .append("<p><a href=\"" + article.url() + "\" style=\"color:#1D70B8;text-decoration:none;\">Read more</a></p>")
                .append("<hr>")
                .append("</div>");
        }

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
            "<table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
            "  <tbody><tr>\n" +
            "    <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
            "      <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
            "        <tbody><tr>\n" +
            "          <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
            "            <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
            "              <tbody><tr>\n" +
            "                <td style=\"padding-left:10px\">\n" +
            "                </td>\n" +
            "                <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
            "                  <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Your Daily News Update</span>\n" +
            "                </td>\n" +
            "              </tr>\n" +
            "            </tbody></table>\n" +
            "          </td>\n" +
            "        </tr>\n" +
            "      </tbody></table>\n" +
            "    </td>\n" +
            "  </tr>\n" +
            "</tbody></table>\n" +
            "<table role=\"presentation\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
            "  <tbody><tr>\n" +
            "    <td height=\"30\"><br></td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
            "      <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Here are the latest news articles related to your interests:</p>\n" +
            newsContent.toString() +
            "      <p>Stay informed and have a great day!</p>\n" +
            "    </td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td height=\"30\"><br></td>\n" +
            "  </tr>\n" +
            "</tbody></table>\n" +
            "</div>";
    }
}
