package dev.EducationSystem;

import dev.EducationSystem.dto.ScheduleDto;
import dev.EducationSystem.dto.request.RequestScheduleDto;
import dev.EducationSystem.entity.Course;
import dev.EducationSystem.entity.Group;
import dev.EducationSystem.entity.Schedule;
import dev.EducationSystem.entity.Teacher;

import dev.EducationSystem.repository.CourseRepository;
import dev.EducationSystem.repository.GroupRepository;
import dev.EducationSystem.repository.ScheduleRepository;
import dev.EducationSystem.repository.TeacherRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import testContainer.AbstractIt;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleServiceIntegrationTest extends AbstractIt {


    @Autowired
    private TestRestTemplate restTemplate;

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
        groupRepository.deleteAll();
        teacherRepository.deleteAll();
        courseRepository.deleteAll();
    }


    @Test
    void shouldFindScheduleByGroup() {

        String url = "/api/v1/schedules/filter"
                + "?groupId=" + group.getId()
                + "&page=0"
                + "&size=10";


        ResponseEntity<PageResponse<RequestScheduleDto>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<RequestScheduleDto>>() {}
                );

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().content().stream().allMatch(dto-> dto.groupId().equals(group.getId())));

    }

    @Test
    void shouldFindScheduleByGroupAndTeacher() {

        String url = "/api/v1/schedules/filter"
                + "?groupId=" + group.getId()
                + "&teacherId=" + teacher.getId()
                + "&page=0"
                + "&size=10";


        ResponseEntity<PageResponse<RequestScheduleDto>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<RequestScheduleDto>>() {}
                );

        assertEquals(200, response.getStatusCode().value());
        assertTrue(
                response.getBody()
                        .content()
                        .stream()
                        .anyMatch(dto -> dto.groupId().equals(group.getId()))
        );

        assertTrue(
                response.getBody()
                        .content()
                        .stream()
                        .anyMatch(dto -> dto.teacherId().equals(teacher.getId()))
        );


    }

    @Test
    void shouldFindScheduleByTeacherAndCourse() {

        String url = "/api/v1/schedules/filter"
                + "?courseId=" + course.getId()
                + "&teacherId=" + teacher.getId()
                + "&page=0"
                + "&size=10";


        ResponseEntity<PageResponse<RequestScheduleDto>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<RequestScheduleDto>>() {}
                );


        assertEquals(200, response.getStatusCode().value());
        assertTrue(
                response.getBody()
                        .content()
                        .stream()
                        .anyMatch(dto -> dto.teacherId().equals(teacher.getId()))
        );

        assertTrue(
                response.getBody()
                        .content()
                        .stream()
                        .anyMatch(dto -> dto.courseId().equals(course.getId()))
        );

    }


    @Test
    void shouldCreateSchedule() {

        Teacher newTeacher = new Teacher();
        newTeacher.setFirstName("Пётр");
        newTeacher.setSecondName("Петров");

        newTeacher = teacherRepository.save(newTeacher);

        TestScheduleDto request = new TestScheduleDto(
                group.getId(),
                newTeacher.getId(),
                course.getId(),
                LocalDateTime.of(2026, 9, 22, 10, 0),
                LocalDateTime.of(2026, 9, 22, 11, 30)
        );

        ResponseEntity<RequestScheduleDto> response =
                restTemplate.postForEntity(
                        "/api/v1/schedules",
                        request,
                        RequestScheduleDto.class
                );

        System.out.println("STATUS = " + response.getStatusCode());
        System.out.println("BODY = " + response.getBody());

        assertEquals(200, response.getStatusCode().value());

        Schedule schedule = scheduleRepository
                .findAll()
                .stream()
                .filter(s ->
                        s.getClassStartDate()
                                .equals(LocalDateTime.of(2026, 9, 22, 10, 0))
                )
                .findFirst()
                .orElseThrow();

        assertEquals(group.getId(), schedule.getGroup().getId());
        assertEquals(newTeacher.getId(), schedule.getTeacher().getId());
        assertEquals(course.getId(), schedule.getCourse().getId());
    }
}