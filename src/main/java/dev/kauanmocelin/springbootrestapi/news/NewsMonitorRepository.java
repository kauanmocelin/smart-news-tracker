package dev.kauanmocelin.springbootrestapi.news;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsMonitorRepository extends JpaRepository<NewsMonitor, Long> {
}
