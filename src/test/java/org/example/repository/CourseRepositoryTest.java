package org.example.repository;

import org.assertj.core.api.Assertions;
import org.example.models.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest

class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();
    }

    @Test
    void saveOneCourse() {
        Course course = new Course("ClassyMath", 10);
        courseRepository.save(course);

        List<Course> courses = courseRepository.findAll();
        assertThat(courses.size()).isOne();
    }

    @Test
    void saveTwoCourses() {
        courseRepository.save(new Course("ClassyMath", 10));
        courseRepository.save(new Course("ClassierMath", 9));

        Course expectedCourse = new Course(2,"ClassierMath", 9);

        List<Course> courses = courseRepository.findAll();
        Optional<Course> classyMath = courses.stream().filter(course -> course.getCourseName().equals("ClassyMath")).findFirst();
        Optional<Course> classierMath = courses.stream().filter(course -> course.getCourseName().equals("ClassierMath")).findFirst();

        assertThat(courses.size()).isEqualTo(2);
        assertThat(classyMath).isPresent();
        assertThat(classierMath).isPresent();
        assertThat(classyMath.get().getClassId()).isEqualTo(classierMath.get().getClassId() -1);
    }

}