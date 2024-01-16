package org.example.services;

import org.example.models.Course;
import org.example.models.Student;
import org.example.models.Teacher;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.example.swinggooey.DatabaseMirror;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Teacher> getTeachers() {
        return databaseMirror.getTeachers();
    }
    public List<Course> getClasses() {
        return databaseMirror.getCourses();
    }
    public List<Student> getStudents() {
        return databaseMirror.getStudents();
    }

    public void setTeacherClasses(int teacherID, int[] listicle) {
        Teacher teach = teacherRepository.findTeacherByID(teacherID);//split the name?
        teach.setHour1(listicle[1]);
        teach.setHour2(listicle[2]);
        teach.setHour3(listicle[3]);
        teach.setHour4(listicle[4]);
        teach.setHour5(listicle[5]);
        teach.setHour6(listicle[6]);
        teach.setHour7(listicle[7]);
        teach.setHour8(listicle[8]);
    }

    public void setStudentClasses(int studentID, int[] listicle) {
        Student stud = studentRepository.findStudentByID(studentID);//split the name?
        stud.setClass1(listicle[0]);//get the actual data from
        stud.setClass2(listicle[1]);
        stud.setClass3(listicle[2]);
        stud.setClass4(listicle[3]);
        stud.setClass5(listicle[4]);
        stud.setClass6(listicle[5]);
        stud.setClass7(listicle[6]);
        stud.setClass8(listicle[7]);
        //stud.setFlag(false);
        System.out.println(stud);
    }

    public void setStudentFlag(int studentID) {
        Student stud = studentRepository.findStudentByID(studentID);
        stud.setFlag(false);
    }
}
