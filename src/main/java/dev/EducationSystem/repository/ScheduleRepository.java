package dev.EducationSystem.repository;


import dev.EducationSystem.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    public Optional<Schedule> findByTeacherId(Long teacherId);
    void deleteByClassEndDateBefore(LocalDateTime date);

}
