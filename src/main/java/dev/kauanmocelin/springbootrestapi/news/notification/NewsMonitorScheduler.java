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
//    @Scheduled(cron = "@daily")
    public void sendMonitoredNewsDaily() {
        final var dailyPeriod = MonitoringPeriod.DAILY;
        try {
            log.info("{} summary news routine executed successfully", dailyPeriod.name());
            newsMonitorResumeEmailSender.sendNewsResumeEmailByPeriod(dailyPeriod);
        } catch(Exception e) {
            log.error("error executing {} summary news routine:{}", dailyPeriod.name(), e.getMessage());
            throw e;
        }
    }

//    @Scheduled(fixedRate = 60000, initialDelay = 60000) // Executa a cada 60 segundos
    @Scheduled(cron = "@weekly")
    public void sendMonitoredNewsWeekly() {
        final var weeklyPeriod = MonitoringPeriod.WEEKLY;
        try {
            log.info("{} summary news routine executed successfully", weeklyPeriod.name());
            newsMonitorResumeEmailSender.sendNewsResumeEmailByPeriod(weeklyPeriod);
        } catch(Exception e) {
            log.error("error executing {} summary news routine:{}", weeklyPeriod.name(), e.getMessage());
            throw e;
        }
    }

    @Scheduled(cron = "@monthly")
    public void sendMonitoredNewsMonthly() {
        final var monthlyPeriod = MonitoringPeriod.MONTHLY;
        try {
            log.info("{} summary news routine executed successfully", monthlyPeriod.name());
            newsMonitorResumeEmailSender.sendNewsResumeEmailByPeriod(monthlyPeriod);
        } catch(Exception e) {
            log.error("error executing {} summary news routine:{}", monthlyPeriod.name(), e.getMessage());
            throw e;
        }
    }
}
