package org.example;

import org.example.models.Course;
import org.example.models.Student;
import org.example.models.Teacher;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.example.services.LabberService;
import org.example.swinggooey.DatabaseMirror;
import org.example.utilities.DigitsOnlyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.Collection;

@Component
public class Labber {
    private JFrame frame;

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    DatabaseMirror databaseMirror;

    LabberService labberService;

    public Labber(LabberService labberService) {
        this.labberService = labberService;
        initialize();
    }

    public JFrame getFrame() {
        return frame;
    }

    private void initialize() {
        if (GraphicsEnvironment.isHeadless()) {//stops problems when running tests.
            return;
        }

        frame = new JFrame("labber");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ((PlainDocument) studentGrade.getDocument()).setDocumentFilter(new DigitsOnlyFilter());
        ((PlainDocument) classGrade.getDocument()).setDocumentFilter(new DigitsOnlyFilter());

        teacherButton.addActionListener(e -> labberService.saveTeacher(teacherFirst.getText(), teacherLast.getText()));

        studentButton.addActionListener(e -> labberService.saveStudent(studentFirst.getText(), studentLast.getText(), Integer.parseInt(studentGrade.getText())));

        classButton.addActionListener(e -> labberService.saveCourse(className.getText(), Integer.parseInt(classGrade.getText())));

        teacherSButton.addActionListener(e -> {
            int[] listicle = new int[9];
            //listicle[0]=(String) teacherNamesList.getSelectedValue();
            listicle[1]=teachHour1List.getSelectedValue().getClassId();
            listicle[2]=teachHour2List.getSelectedValue().getClassId();
            listicle[3]=teachHour3List.getSelectedValue().getClassId();
            listicle[4]=teachHour4List.getSelectedValue().getClassId();
            listicle[5]=teachHour5List.getSelectedValue().getClassId();
            listicle[6]=teachHour6List.getSelectedValue().getClassId();
            listicle[7]=teachHour7List.getSelectedValue().getClassId();
            listicle[8]=teachHour8List.getSelectedValue().getClassId();

            labberService.setTeacherClasses((String) teacherNamesList.getSelectedValue(), listicle);
        });//set teacher classes

        studentSaveButton.addActionListener(e -> {
            int[] listicle = new int[8];
            listicle[0]=studentClass1.getSelectedValue().getClassId();
            listicle[1]=studentClass2.getSelectedValue().getClassId();
            listicle[2]=studentClass3.getSelectedValue().getClassId();
            listicle[3]=studentClass4.getSelectedValue().getClassId();
            listicle[4]=studentClass5.getSelectedValue().getClassId();
            listicle[5]=studentClass6.getSelectedValue().getClassId();
            listicle[6]=studentClass7.getSelectedValue().getClassId();
            listicle[7]=studentClass8.getSelectedValue().getClassId();

            labberService.setStudentClasses((String) studentNamesList.getSelectedValue(), listicle);
            labberService.setStudentFlag((String) studentNamesList.getSelectedValue());
        });//set student classes

        studentScheduleButton.addActionListener(e ->  {
            //setStudentClasses(studentNamesList);
            //int[] studentScheduledClasses = labberService.getStudentScheduledClasses((Integer) studentNamesList.getSelectedValue());
            //setStudentClasses(studentNamesList, studentScheduledClasses);

            int[] listicle = new int[8];
            listicle[0]=studentClass1.getSelectedValue().getClassId();
            listicle[1]=studentClass2.getSelectedValue().getClassId();
            listicle[2]=studentClass3.getSelectedValue().getClassId();
            listicle[3]=studentClass4.getSelectedValue().getClassId();
            listicle[4]=studentClass5.getSelectedValue().getClassId();
            listicle[5]=studentClass6.getSelectedValue().getClassId();
            listicle[6]=studentClass7.getSelectedValue().getClassId();
            listicle[7]=studentClass8.getSelectedValue().getClassId();
            labberService.setStudentClasses((String) studentNamesList.getSelectedValue(), listicle);
            labberService.setStudentFlag((String) studentNamesList.getSelectedValue());

            int[] studentScheduledClasses = labberService.getStudentScheduledClasses((Integer) studentNamesList.getSelectedValue());

            labberService.setStudentClasses((String) studentNamesList.getSelectedValue(), studentScheduledClasses);

            //System.out.println();
        });//attempt to schedule them

        tabbedPane1.addChangeListener(e -> {
           //List teachers= databaseMirror.getTeachers();
           //teacherNamesList.addElement("aa");
            if(tabbedPane1.getSelectedIndex()==3) {
                teacherNamesListModel.clear();
                Collection teachers = labberService.getTeachers();
                teacherNamesListModel.addAll(teachers);
                classNamesListModel.clear();
                Collection classes = labberService.getClasses();
                classNamesListModel.addAll(classes);
            }
        });

        teacherNamesList.setModel(teacherNamesListModel);
        studentNamesList.setModel(studentNamesListModel);
        teachHour1List.setModel(classNamesListModel);
        teachHour2List.setModel(classNamesListModel);
        teachHour3List.setModel(classNamesListModel);
        teachHour4List.setModel(classNamesListModel);
        teachHour5List.setModel(classNamesListModel);
        teachHour6List.setModel(classNamesListModel);
        teachHour7List.setModel(classNamesListModel);
        teachHour8List.setModel(classNamesListModel);
        studentClass1.setModel(classNamesListModel);
        studentClass2.setModel(classNamesListModel);
        studentClass3.setModel(classNamesListModel);
        studentClass4.setModel(classNamesListModel);
        studentClass5.setModel(classNamesListModel);
        studentClass6.setModel(classNamesListModel);
        studentClass7.setModel(classNamesListModel);
        studentClass8.setModel(classNamesListModel);

        frame.pack();


    }


    private void setTeacherClasses(JList teacherNamesList) {
        //get teacher row by...something
        Teacher teach = teacherRepository.findTeacherByName((String) teacherNamesList.getSelectedValue());//split the name?
        teach.setHour1(courseRepository.courseIDbyName(teachHour1List.getSelectedValue().getClassId()));//get the actual data from
        teach.setHour2(courseRepository.courseIDbyName(teachHour2List.getSelectedValue().getClassId()));
        teach.setHour3(courseRepository.courseIDbyName(teachHour3List.getSelectedValue().getClassId()));
        teach.setHour4(courseRepository.courseIDbyName(teachHour4List.getSelectedValue().getClassId()));
        teach.setHour5(courseRepository.courseIDbyName(teachHour5List.getSelectedValue().getClassId()));
        teach.setHour6(courseRepository.courseIDbyName(teachHour6List.getSelectedValue().getClassId()));
        teach.setHour7(courseRepository.courseIDbyName(teachHour7List.getSelectedValue().getClassId()));
        teach.setHour8(courseRepository.courseIDbyName(teachHour8List.getSelectedValue().getClassId()));
    }

    private void setStudentClasses(JList studentNamesList) {
        Student stud = studentRepository.findStudentByName((String) studentNamesList.getSelectedValue());//split the name?
        stud.setClass1(courseRepository.courseIDbyName(studentClass1.getSelectedValue().getClassId()));//get the actual data from
        stud.setClass2(courseRepository.courseIDbyName(studentClass2.getSelectedValue().getClassId()));
        stud.setClass3(courseRepository.courseIDbyName(studentClass3.getSelectedValue().getClassId()));
        stud.setClass4(courseRepository.courseIDbyName(studentClass4.getSelectedValue().getClassId()));
        stud.setClass5(courseRepository.courseIDbyName(studentClass5.getSelectedValue().getClassId()));
        stud.setClass6(courseRepository.courseIDbyName(studentClass6.getSelectedValue().getClassId()));
        stud.setClass7(courseRepository.courseIDbyName(studentClass7.getSelectedValue().getClassId()));
        stud.setClass8(courseRepository.courseIDbyName(studentClass8.getSelectedValue().getClassId()));
        stud.setFlag(false);
    }

    private void setStudentClasses(JList studentNamesList, int[] scheduledCourses) {
        Student stud = studentRepository.findStudentByName((String) studentNamesList.getSelectedValue());//split the name?
        stud.setClass1(scheduledCourses[0]);
        stud.setClass2(scheduledCourses[1]);
        stud.setClass3(scheduledCourses[2]);
        stud.setClass4(scheduledCourses[3]);
        stud.setClass5(scheduledCourses[4]);
        stud.setClass6(scheduledCourses[5]);
        stud.setClass7(scheduledCourses[6]);
        stud.setClass8(scheduledCourses[7]);
        //and don't touch the flag
    }

    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField teacherFirst;
    private JTextField teacherLast;
    private JButton teacherButton;
    private JTextField studentFirst;
    private JTextField studentLast;
    private JTextField studentGrade;
    private JButton studentButton;
    private JTextField className;
    private JTextField classGrade;
    private JButton classButton;
    private JList teacherNamesList;
    DefaultListModel teacherNamesListModel = new DefaultListModel();
    private JList<Course> teachHour1List;
    DefaultListModel classNamesListModel = new DefaultListModel();
    private JList<Course> teachHour2List;
    private JList<Course> teachHour3List;
    private JList<Course> teachHour4List;
    private JList<Course> teachHour5List;
    private JList<Course> teachHour6List;
    private JList<Course> teachHour7List;
    private JList<Course> teachHour8List;
    private JButton teacherSButton;
    private JList studentNamesList;
    DefaultListModel studentNamesListModel = new DefaultListModel();
    private JList<Course> studentClass1;
    private JList<Course> studentClass2;
    private JList<Course> studentClass3;
    private JList<Course> studentClass4;
    private JList<Course> studentClass5;
    private JList<Course> studentClass6;
    private JList<Course> studentClass7;
    private JList<Course> studentClass8;
    private JButton studentScheduleButton;
    private JPanel Scheduler;
    private JButton generateScheduleSButton;
    private JButton studentSaveButton;
    private JRadioButton radioButton1;


}