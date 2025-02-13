package dev.kauanmocelin.springbootrestapi.news.request;

import dev.kauanmocelin.springbootrestapi.news.MonitoringPeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record KeywordRequest(

    @NotBlank(message = "The keyword cannot be empty")
    @Size(min = 3, max = 30)
    @Schema(description = "This keyword is used for search news", example = "java")
    String keyword,

    @NotNull(message = "The monitoringPeriod cannot be empty")
    @Schema(description = "This monitoring period is used for frequency schedule", example = "DAILY")
    MonitoringPeriod monitoringPeriod
) {
}
