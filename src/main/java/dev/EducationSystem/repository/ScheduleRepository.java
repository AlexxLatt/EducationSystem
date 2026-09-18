package dev.EducationSystem.repository;


import dev.EducationSystem.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> , JpaSpecificationExecutor<Schedule> {

    public List<Schedule> findByTeacherId(Long teacherId);
    void deleteByClassEndDateBefore(LocalDateTime date);

}
