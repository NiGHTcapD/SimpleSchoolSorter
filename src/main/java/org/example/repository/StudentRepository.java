package org.example.repository;

import org.example.models.Student;
import org.example.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("select stud from Student stud where stud.IDgnorable = :studentID")
    Student findStudentByID(@Param("studentID") int studentID);

    @Query("select stud from Student stud where stud.studentFirstName = :firstName")
    Student findStudentByName(@Param("firstName") String firstName);


    //findStudentsClasses


    @Query("select COUNT(*) from Student stud where stud.class1 = :course")
    int findClassAllocation1(@Param("course") int course);
    //SELECT COUNT(*)
    //FROM table_name
    //WHERE condition;
    @Query("select COUNT(*) from Student stud where stud.class2 = :course")
    int findClassAllocation2(@Param("course") int course);
    @Query("select COUNT(*) from Student stud where stud.class3 = :course")
    int findClassAllocation3(@Param("course") int course);
    @Query("select COUNT(*) from Student stud where stud.class4 = :course")
    int findClassAllocation4(@Param("course") int course);
    @Query("select COUNT(*) from Student stud where stud.class5 = :course")
    int findClassAllocation5(@Param("course") int course);
    @Query("select COUNT(*) from Student stud where stud.class6 = :course")
    int findClassAllocation6(@Param("course") int course);
    @Query("select COUNT(*) from Student stud where stud.class7 = :course")
    int findClassAllocation7(@Param("course") int course);
    @Query("select COUNT(*) from Student stud where stud.class8 = :course")
    int findClassAllocation8(@Param("course") int course);



}
