package org.example.swinggooey;

import org.assertj.core.api.Assertions;
import org.example.models.Course;
import org.example.models.Teacher;
import org.example.repository.CourseRepository;
import org.example.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseMirrorTest {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    DatabaseMirror databaseMirror;

    @BeforeEach
    void setUp() {teacherRepository.deleteAll();
    courseRepository.deleteAll();}

    @Test
    void mirrorTeachers() {
        teacherRepository.save(new Teacher("FirstName", "LastName"));
        teacherRepository.save(new Teacher("Actress", "Again"));
        teacherRepository.save(new Teacher("Three", "Scrowd"));
        teacherRepository.save(new Teacher("Timewarp", "Again"));

        databaseMirror.teacherMirror();
        List<Teacher> teachers = databaseMirror.getTeachers();
        assertThat(teachers.size()).isEqualTo(4);
    }

    @Test
    void mirrorCourses() {
        courseRepository.save(new Course("math", 9));
        courseRepository.save(new Course("myth", 10));
        courseRepository.save(new Course("moth", 11));
        courseRepository.save(new Course("meth", 12));

        databaseMirror.courseMirror();
        List<Course> courses = databaseMirror.getCourses();
        assertThat(courses.size()).isEqualTo(4);
    }
}