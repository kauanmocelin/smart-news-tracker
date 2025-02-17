package dev.kauanmocelin.springbootrestapi.news;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@ActiveProfiles("test")
class NewsMonitorServiceTestApiReal {

    @Autowired
    private NewsMonitorService newsMonitorService;

    @Test
    void shouldFetchNewsFromYesterdaySuccessfully() {
        String keyword = "Java";
        var response = newsMonitorService.fetchNewsFromYesterday(keyword);

        assertThat(response).isNotNull();
        System.out.println(response);
    }
}