package dev.kauanmocelin.springbootrestapi.news;

import dev.kauanmocelin.springbootrestapi.appuser.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "news_monitors")
public class NewsMonitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The keyword cannot be empty")
    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String keyword;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The monitoring period cannot be empty")
    @Column(nullable = false)
    private MonitoringPeriod monitoringPeriod;

    @ManyToOne
    @JoinColumn(name = "app_users_id")
    private AppUser appUser;

}
