package dev.educationsystem.schedule;

import dev.educationsystem.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Transactional
@RequiredArgsConstructor
public class ScheduleCleanupService {
    private final ScheduleRepository scheduleRepository;

    @Scheduled(cron = "${app.scheduler.delete-old-schedules.cron}")
    public void deleteOldSchedules() {
        LocalDateTime limit = LocalDateTime.now().minusYears(1);
        scheduleRepository.deleteByClassEndDateBefore(limit);
    }


}
