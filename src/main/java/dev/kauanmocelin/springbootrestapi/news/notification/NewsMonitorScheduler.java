package dev.kauanmocelin.springbootrestapi.news.notification;

import dev.kauanmocelin.springbootrestapi.news.MonitoringPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsMonitorScheduler {

    private final NewsMonitorResumeEmailSender newsMonitorResumeEmailSender;

    @Scheduled(fixedRate = 30000) // Executa a cada 30 segundos
    @Scheduled(cron = "@daily")
    public void sendMonitoredNewsDaily() {
        final var dailyPeriod = MonitoringPeriod.DAILY;
        try {
            newsMonitorResumeEmailSender.sendNewsResumeEmailByPeriod(dailyPeriod);
            log.info("Scheduled {} task completed successfully", dailyPeriod.name());
        } catch(Exception e) {
            log.error("Error executing {} scheduled task:{}", dailyPeriod.name(), e.getMessage());
            throw e;
        }
    }

    //    @Scheduled(cron = "0 0 8 * * MON") // Uma vez por semana (na segunda-feira às 08:00)
    @Scheduled(fixedRate = 60000) // Executa a cada 60 segundos
    @Scheduled(cron = "@weekly")
    public void sendMonitoredNewsWeekly() {
        final var weeklyPeriod = MonitoringPeriod.WEEKLY;
        try {
            newsMonitorResumeEmailSender.sendNewsResumeEmailByPeriod(weeklyPeriod);
            log.info("Scheduled {} task completed successfully", weeklyPeriod.name());
        } catch(Exception e) {
            log.error("Error executing {} scheduled task:{}", weeklyPeriod.name(), e.getMessage());
            throw e;
        }
    }

    @Scheduled(cron = "@monthly")
    public void sendMonitoredNewsMonthly() {
        final var monthlyPeriod = MonitoringPeriod.MONTHLY;
        try {
            newsMonitorResumeEmailSender.sendNewsResumeEmailByPeriod(monthlyPeriod);
            log.info("Scheduled {} task completed successfully", monthlyPeriod.name());
        } catch(Exception e) {
            log.error("Error executing {} scheduled task:{}", monthlyPeriod.name(), e.getMessage());
            throw e;
        }
    }
}
