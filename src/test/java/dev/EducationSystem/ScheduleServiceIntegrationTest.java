package dev.EducationSystem;

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

import org.springframework.http.ResponseEntity;
import testContainer.AbstractIt;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=8085"
)
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
    }


    @Test
    void shouldFindScheduleByGroup() {

        String url = "http://localhost:8085/api/v1/schedules/filter"
                + "?groupId=" + group.getId()
                + "&page=0"
                + "&size=10";


        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(200, response.getStatusCode().value());
        System.out.println(response.getBody());

        assertTrue(response.getBody().contains("\"groupId\":" + group.getId()));

        assertTrue(response.getBody().contains("\"courseId\":" + course.getId()));
        assertTrue(response.getBody().contains("\"teacherId\":" + teacher.getId()));
    }
}