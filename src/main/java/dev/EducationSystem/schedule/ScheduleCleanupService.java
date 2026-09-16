package dev.EducationSystem.schedule;

import dev.EducationSystem.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleCleanupService {
    private final ScheduleRepository scheduleRepository;

    @Scheduled(cron = "0 */5 * * * *")
    public void deleteOldSchedules() {
        LocalDateTime limit = LocalDateTime.now().minusDays(1);
        scheduleRepository.deleteByClassEndDateBefore(limit);
    }


}
