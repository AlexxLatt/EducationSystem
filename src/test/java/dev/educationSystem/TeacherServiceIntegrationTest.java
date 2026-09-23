package dev.educationSystem;

import dev.educationSystem.dto.request.TeacherResponseDto;
import dev.educationSystem.entity.Teacher;
import dev.educationSystem.repository.TeacherRepository;
import dev.educationSystem.service.TeacherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import testContainer.AbstractIt;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeacherServiceIntegrationTest  extends AbstractIt {

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

        TeacherResponseDto result = teacherService.getTeacher(savedTeacher.getId());

        assertEquals("Иван", result.firstName());
        assertEquals("Иванов", result.secondName());
    }


}