package dev.EducationSystem;

import dev.EducationSystem.dto.request.RequestTeacherDto;
import dev.EducationSystem.entity.Teacher;
import dev.EducationSystem.repository.TeacherRepository;
import dev.EducationSystem.service.TeacherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeacherServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teacherService;

    private Teacher savedTeacher;

    @BeforeEach
    void setUp() {
        Teacher teacher = new Teacher();

        teacher.setFirstName("Иван");
        teacher.setSecondName("Иванов");

        savedTeacher = teacherRepository.save(teacher);
    }


    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    void shouldFindTeacherById() {

        RequestTeacherDto result = teacherService.getTeacher(savedTeacher.getId());

        assertEquals("Иван", result.firstName());
        assertEquals("Иванов", result.secondName());
    }


}