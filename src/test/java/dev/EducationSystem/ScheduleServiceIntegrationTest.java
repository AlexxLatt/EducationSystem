package dev.EducationSystem;

import dev.EducationSystem.dto.request.RequestScheduleDto;
import dev.EducationSystem.dto.request.RequestTeacherDto;
import dev.EducationSystem.entity.Course;
import dev.EducationSystem.entity.Group;
import dev.EducationSystem.entity.Schedule;
import dev.EducationSystem.entity.Teacher;
import dev.EducationSystem.filter.ScheduleFilter;
import dev.EducationSystem.repository.CourseRepository;
import dev.EducationSystem.repository.GroupRepository;
import dev.EducationSystem.repository.ScheduleRepository;
import dev.EducationSystem.repository.TeacherRepository;
import dev.EducationSystem.service.ScheduleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import testContainer.AbstractIt;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleServiceIntegrationTest extends AbstractIt {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Group group;
    private Teacher teacher;
    private Course course;


    @BeforeEach
    void setUp() {

        group = new Group();
        group.setName("Группа 101");


        teacher = new Teacher();
        teacher.setFirstName("Александр");
        teacher.setSecondName("Иванов");

        course = new Course();
        course.setName("Математика");
        course.setDescription("Высшая математика");

        group = groupRepository.save(group);
        teacher = teacherRepository.save(teacher);
        course = courseRepository.save(course);


        Schedule schedule = new Schedule();

        schedule.setGroup(group);
        schedule.setTeacher(teacher);
        schedule.setCourse(course);

        schedule.setClassStartDate(
                LocalDateTime.of(2026, 9, 21, 10, 0)
        );

        schedule.setClassEndDate(
                LocalDateTime.of(2026, 9, 21, 11, 30)
        );

        scheduleRepository.save(schedule);
    }


    @AfterEach
    void tearDown() {
        scheduleRepository.deleteAll();
    }


    @Test
    void shouldFindScheduleByGroup() {

        Pageable pageable = PageRequest.of(0, 10);

        ScheduleFilter scheduleFilter = new ScheduleFilter();
        scheduleFilter.setGroupId(group.getId());


        Page<RequestScheduleDto> result =
                scheduleService.findScheduleCursesForGroup(
                        scheduleFilter,
                        pageable
                );


        assertEquals(1, result.getTotalElements());

        RequestScheduleDto schedule = result.getContent().get(0);


        assertEquals(group.getId(), schedule.groupId());

        assertEquals(course.getId(), schedule.courseId());

        assertEquals(teacher.getId(), schedule.teacherId());
    }
}