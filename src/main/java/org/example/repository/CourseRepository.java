package org.example.repository;

import org.example.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("select class.classId from Course class where class.courseName = :className")
    int courseIDbyName(@Param("className") String className);
}

