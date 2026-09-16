package dev.EducationSystem.repository;

import dev.EducationSystem.entity.Course;
import dev.EducationSystem.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
