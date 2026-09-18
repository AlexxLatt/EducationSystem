package dev.EducationSystem.specifications;

import dev.EducationSystem.entity.Schedule;
import dev.EducationSystem.filter.ScheduleFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;


public class ScheduleSpecifications {
    public  static Specification<Schedule> findScheduleCursesForGroup(ScheduleFilter scheduleFilter){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(scheduleFilter.getGroupId() != null){

                predicates.add(cb.equal(root.get("group").get("id"), scheduleFilter.getGroupId()));

            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
