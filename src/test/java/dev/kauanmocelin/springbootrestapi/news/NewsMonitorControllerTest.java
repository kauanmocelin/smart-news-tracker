package dev.kauanmocelin.springbootrestapi.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kauanmocelin.springbootrestapi.common.security.JwtAuthenticationFilter;
import dev.kauanmocelin.springbootrestapi.common.security.JwtService;
import dev.kauanmocelin.springbootrestapi.news.request.KeywordRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = NewsMonitorController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class
)
@AutoConfigureMockMvc(addFilters = false)
class NewsMonitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NewsMonitorService newsMonitorService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @DisplayName("Should return status created when register new keyword")
    void shouldReturnStatusCreatedWhenRegisterNewKeyword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news-monitor/keyword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new KeywordRequest("java", MonitoringPeriod.DAILY))))
            .andExpect(status().isCreated());
    }
}