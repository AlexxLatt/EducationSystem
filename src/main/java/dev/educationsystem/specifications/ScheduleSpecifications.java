package dev.educationsystem.specifications;

import dev.educationsystem.entity.Schedule;
import dev.educationsystem.dto.request.ScheduleFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;


public class ScheduleSpecifications {
    public static Specification<Schedule> findScheduleCursesForGroup(ScheduleFilter scheduleFilter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (scheduleFilter.getGroupId() != null) {

                predicates.add(cb.equal(root.get("group").get("id"), scheduleFilter.getGroupId()));

            }
            if (scheduleFilter.getCourseId() != null) {

                predicates.add(cb.equal(root.get("course").get("id"), scheduleFilter.getCourseId()));

            }
            if (scheduleFilter.getTeacherId() != null) {

                predicates.add(cb.equal(root.get("teacher").get("id"), scheduleFilter.getTeacherId()));

            }
            if (scheduleFilter.getClassStartDate() != null) {

                predicates.add(cb.equal(root.get("classStartDate"), scheduleFilter.getClassStartDate()));

            }
            if (scheduleFilter.getClassEndDate() != null) {

                predicates.add(cb.equal(root.get("classEndDate"), scheduleFilter.getClassEndDate()));

            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
