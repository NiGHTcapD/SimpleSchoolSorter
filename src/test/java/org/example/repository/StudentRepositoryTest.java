package org.example.repository;

import org.assertj.core.api.Assertions;
import org.example.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
class StudentRepositoryTest {


    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void saveStudent() {
        Student student = new Student("FirstName", "LastName", 10);
        studentRepository.save(student);

        List<Student> students = studentRepository.findAll();
        Assertions.assertThat(students.size()).isOne();
    }
}