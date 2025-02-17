package dev.kauanmocelin.springbootrestapi.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kauanmocelin.springbootrestapi.common.security.JwtAuthenticationFilter;
import dev.kauanmocelin.springbootrestapi.news.request.KeywordRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    value = NewsMonitorController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class,
    excludeFilters =
    @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtAuthenticationFilter.class))
@DisplayName("Tests for News Monitor controller")
class NewsMonitorControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NewsMonitorService newsMonitorService;

    @Test
    @DisplayName("Should return status created when register new keyword")
    void shouldReturnStatusCreatedWhenRegisterNewKeyword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news-monitor/keyword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new KeywordRequest("java", MonitoringPeriod.DAILY))))
            .andExpect(status().isCreated());
    }
}