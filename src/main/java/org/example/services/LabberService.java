package org.example.services;

import org.example.Labber;
import org.example.models.Course;
import org.example.models.Student;
import org.example.models.Teacher;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.example.swinggooey.DatabaseMirror;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LabberService {
    @Autowired
    StudentsIntoClasses studentsIntoClasses;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    DatabaseMirror databaseMirror;


    public int[] getStudentScheduledClasses(Integer id) {
        return studentsIntoClasses.studentScheduler(id);
    }

    public void saveTeacher(String firstName, String lastName) {
        Teacher teacher = new Teacher(firstName, lastName);
        teacherRepository.save(teacher);
    }

    public void saveStudent(String studentFirst, String studentLast, int studentGrade) {
        Student student = new Student(studentFirst, studentLast, studentGrade);
        studentRepository.save(student);
    }

    public void saveCourse(String className, int classGrade) {
        Course course = new Course(className, classGrade);
        courseRepository.save(course);
    }

    public Collection getTeachers() {
        return databaseMirror.getTeachers();
    }
    public Collection getClasses() {
        return databaseMirror.getCourses();
    }
    public Collection getStudents() {
        return databaseMirror.getStudents();
    }

    public void setTeacherClasses(String teacherName, int[] listicle) {
        Teacher teach = teacherRepository.findTeacherByName(teacherName);//split the name?
        teach.setHour1(courseRepository.courseIDbyName(listicle[1]));//get the actual data from
        teach.setHour2(courseRepository.courseIDbyName(listicle[2]));
        teach.setHour3(courseRepository.courseIDbyName(listicle[3]));
        teach.setHour4(courseRepository.courseIDbyName(listicle[4]));
        teach.setHour5(courseRepository.courseIDbyName(listicle[5]));
        teach.setHour6(courseRepository.courseIDbyName(listicle[6]));
        teach.setHour7(courseRepository.courseIDbyName(listicle[7]));
        teach.setHour8(courseRepository.courseIDbyName(listicle[8]));
    }

    public void setStudentClasses(String studentName, int[] listicle) {
        Student stud = studentRepository.findStudentByName(studentName);//split the name?
        stud.setClass1(courseRepository.courseIDbyName(listicle[0]));//get the actual data from
        stud.setClass2(courseRepository.courseIDbyName(listicle[1]));
        stud.setClass3(courseRepository.courseIDbyName(listicle[2]));
        stud.setClass4(courseRepository.courseIDbyName(listicle[3]));
        stud.setClass5(courseRepository.courseIDbyName(listicle[4]));
        stud.setClass6(courseRepository.courseIDbyName(listicle[5]));
        stud.setClass7(courseRepository.courseIDbyName(listicle[6]));
        stud.setClass8(courseRepository.courseIDbyName(listicle[7]));
        //stud.setFlag(false);
        System.out.println(stud);
    }

    public void setStudentFlag(String studentName) {
        Student stud = studentRepository.findStudentByName(studentName);
        stud.setFlag(false);
    }
}
