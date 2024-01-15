package org.example.repository;

import org.assertj.core.api.Assertions;
import org.example.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        teacherRepository.deleteAll();
    }

    @Test
    void saveTeacher() {
        Teacher teacher = new Teacher("FirstName", "LastName");
        teacherRepository.save(teacher);

        List<Teacher> teachers = teacherRepository.findAll();
        Assertions.assertThat(teachers.size()).isOne();
    }
}