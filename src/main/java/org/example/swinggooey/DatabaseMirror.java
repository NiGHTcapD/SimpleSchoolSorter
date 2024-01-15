package org.example.swinggooey;

import org.example.models.Course;
import org.example.models.Student;
import org.example.models.Teacher;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class DatabaseMirror {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;

    private List<Teacher> teachers;
    private List<Course> courses;
    private List<Student> students;

    public void teacherMirror() {
        teachers = teacherRepository.findAll();
    }
    public List<Teacher> getTeachers(){
        teacherMirror();
        return teachers;
    }
    public void courseMirror() {
        courses = courseRepository.findAll();
    }
    public List<Course> getCourses(){
        courseMirror();
        return courses;
    }
    public void studentMirror() {
        students = studentRepository.findAll();
    }
    public List<Student> getStudents(){
        studentMirror();
        return students;
    }

}
