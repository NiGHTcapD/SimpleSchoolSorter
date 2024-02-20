package org.example;

import org.example.models.Course;
import org.example.models.Student;
import org.example.models.Teacher;
import org.example.services.LabberService;
import org.example.utilities.AutocompleteComboBox;
import org.example.utilities.DigitsOnlyFilter;
import org.example.utilities.FilterComboBox;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.List;

@Component
public class Labber {
    private JFrame frame;


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
            listicle[1]=((Course)teachHour1List.getSelectedItem()).getClassId();
            listicle[2]=((Course)teachHour2List.getSelectedItem()).getClassId();
            listicle[3]=((Course)teachHour3List.getSelectedItem()).getClassId();
            listicle[4]=((Course)teachHour4List.getSelectedItem()).getClassId();
            listicle[5]=((Course)teachHour5List.getSelectedItem()).getClassId();
            listicle[6]=((Course)teachHour6List.getSelectedItem()).getClassId();
            listicle[7]=((Course)teachHour7List.getSelectedItem()).getClassId();
            listicle[8]=((Course)teachHour8List.getSelectedItem()).getClassId();

            labberService.setTeacherClasses((Teacher) teacherNamesList.getSelectedItem(), listicle);
        });//set teacher classes

        studentSaveButton.addActionListener(e -> {
            int[] listicle = new int[8];
            listicle[0]=((Course)studentClass1.getSelectedItem()).getClassId();
            listicle[1]=((Course)studentClass2.getSelectedItem()).getClassId();
            listicle[2]=((Course)studentClass3.getSelectedItem()).getClassId();
            listicle[3]=((Course)studentClass4.getSelectedItem()).getClassId();
            listicle[4]=((Course)studentClass5.getSelectedItem()).getClassId();
            listicle[5]=((Course)studentClass6.getSelectedItem()).getClassId();
            listicle[6]=((Course)studentClass7.getSelectedItem()).getClassId();
            listicle[7]=((Course)studentClass8.getSelectedItem()).getClassId();

            labberService.setStudentClasses((Student) studentNamesList.getSelectedItem(), listicle);
            labberService.setStudentFlag((Student) studentNamesList.getSelectedItem());
        });//set student classes

        studentScheduleButton.addActionListener(e ->  {
            //setStudentClasses(studentNamesList);
            //int[] studentScheduledClasses = labberService.getStudentScheduledClasses((Integer) studentNamesList.getSelectedValue());
            //setStudentClasses(studentNamesList, studentScheduledClasses);

            int[] listicle = new int[8];
            listicle[0]=((Course)studentClass1.getSelectedItem()).getClassId();
            listicle[1]=((Course)studentClass2.getSelectedItem()).getClassId();
            listicle[2]=((Course)studentClass3.getSelectedItem()).getClassId();
            listicle[3]=((Course)studentClass4.getSelectedItem()).getClassId();
            listicle[4]=((Course)studentClass5.getSelectedItem()).getClassId();
            listicle[5]=((Course)studentClass6.getSelectedItem()).getClassId();
            listicle[6]=((Course)studentClass7.getSelectedItem()).getClassId();
            listicle[7]=((Course)studentClass8.getSelectedItem()).getClassId();
            labberService.setStudentClasses((Student) studentNamesList.getSelectedItem(), listicle);
            labberService.setStudentFlag((Student) studentNamesList.getSelectedItem());

            Pair<Integer, Integer>[] studentScheduledClasses = labberService.getStudentScheduledClasses((Student) studentNamesList.getSelectedItem());

            if (studentScheduledClasses[0].getFirst()!=0) {
                labberService.setStudentClasses((Student) studentNamesList.getSelectedItem(), studentScheduledClasses);
            }
            //System.out.println();
        });//attempt to schedule them

        generateScheduleSButton.addActionListener(e ->{
            //loop through all students with false flags and attempt to schedule them
//
            //get all students
            for (Student student:
                    labberService.getStudents()) {
                //if they have flag set to false
                if (!student.isFlag()) {
                    //schedule them
                    Pair<Integer, Integer>[] studentScheduledClasses = labberService.getStudentScheduledClasses(student);
                    if (studentScheduledClasses[0].getFirst()!=0) {
                        labberService.setStudentClasses(student, studentScheduledClasses);
                    }
                }
            }
        });

        tabbedPane1.addChangeListener(e -> {
           //List teachers= databaseMirror.getTeachers();
           //teacherNamesList.addElement("aa");
            if(tabbedPane1.getSelectedIndex()==3) {
                teacherNamesList.setItems(labberService.getTeachers());

                teachHour1List.setItems(labberService.getClasses());
                teachHour2List.setItems(labberService.getClasses());
                teachHour3List.setItems(labberService.getClasses());
                teachHour4List.setItems(labberService.getClasses());
                teachHour5List.setItems(labberService.getClasses());
                teachHour6List.setItems(labberService.getClasses());
                teachHour7List.setItems(labberService.getClasses());
                teachHour8List.setItems(labberService.getClasses());
                /*teacherNamesListModel.clear();
                List<Teacher> teachers = labberService.getTeachers();
                teacherNamesListModel.addAll(teachers);
                classNamesListModel.clear();
                List<Course> classes = labberService.getClasses();
                classNamesListModel.addAll(classes);*/
            }
            if(tabbedPane1.getSelectedIndex()==4) {
                studentNamesList.setItems(labberService.getStudents());

                studentClass1.setItems(labberService.getClasses());
                studentClass2.setItems(labberService.getClasses());
                studentClass3.setItems(labberService.getClasses());
                studentClass4.setItems(labberService.getClasses());
                studentClass5.setItems(labberService.getClasses());
                studentClass6.setItems(labberService.getClasses());
                studentClass7.setItems(labberService.getClasses());
                studentClass8.setItems(labberService.getClasses());
            }
        });

        teacherNamesList.addActionListener(e -> {

            Teacher teacher = (Teacher) teacherNamesList.getSelectedItem();
            if (teacher != null) {
                teachHour1List.setSelectedIndex(getClassListIndex(teacher.getHour1()));
                teachHour2List.setSelectedIndex(getClassListIndex(teacher.getHour2()));
                teachHour3List.setSelectedIndex(getClassListIndex(teacher.getHour3()));
                teachHour4List.setSelectedIndex(getClassListIndex(teacher.getHour4()));
                teachHour5List.setSelectedIndex(getClassListIndex(teacher.getHour5()));
                teachHour6List.setSelectedIndex(getClassListIndex(teacher.getHour6()));
                teachHour7List.setSelectedIndex(getClassListIndex(teacher.getHour7()));
                teachHour8List.setSelectedIndex(getClassListIndex(teacher.getHour8()));
                // Use this teacher to set the rest
            }


        });
        studentNamesList.addActionListener(e -> {
            Student student = (Student) studentNamesList.getSelectedItem();
            if (student != null) {
                studentClass1.setSelectedIndex(getClassListIndex(student.getClass1()));
                studentClass2.setSelectedIndex(getClassListIndex(student.getClass2()));
                studentClass3.setSelectedIndex(getClassListIndex(student.getClass3()));
                studentClass4.setSelectedIndex(getClassListIndex(student.getClass4()));
                studentClass5.setSelectedIndex(getClassListIndex(student.getClass5()));
                studentClass6.setSelectedIndex(getClassListIndex(student.getClass6()));
                studentClass7.setSelectedIndex(getClassListIndex(student.getClass7()));
                studentClass8.setSelectedIndex(getClassListIndex(student.getClass8()));
                // Use this teacher to set the rest
            }
        });
        frame.pack();


    }

    private int getClassListIndex(int classID) {
        //one way to get the index is to have already stored the objects in an array
        //oh look, I have one
        for (int i = 0; i < classNamesListModel.getSize(); i++) {

            if (classNamesListModel.getElementAt(i).getClassId()==classID) {
                return i;
            }
        }
        return -1;
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
    private FilterComboBox<Teacher> teacherNamesList;
    DefaultListModel<Teacher> teacherNamesListModel = new DefaultListModel<>();
    private FilterComboBox<Course> teachHour1List;
    DefaultListModel<Course> classNamesListModel = new DefaultListModel<>();
    private FilterComboBox<Course> teachHour2List;
    private FilterComboBox<Course> teachHour3List;
    private FilterComboBox<Course> teachHour4List;
    private FilterComboBox<Course> teachHour5List;
    private FilterComboBox<Course> teachHour6List;
    private FilterComboBox<Course> teachHour7List;
    private FilterComboBox<Course> teachHour8List;
    private JButton teacherSButton;
    private FilterComboBox<Student> studentNamesList;
    DefaultListModel<Student> studentNamesListModel = new DefaultListModel<>();
    private FilterComboBox<Course> studentClass1;
    private FilterComboBox<Course> studentClass2;
    private FilterComboBox<Course> studentClass3;
    private FilterComboBox<Course> studentClass4;
    private FilterComboBox<Course> studentClass5;
    private FilterComboBox<Course> studentClass6;
    private FilterComboBox<Course> studentClass7;
    private FilterComboBox<Course> studentClass8;
    private JButton studentScheduleButton;
    private JPanel Scheduler;
    private JButton generateScheduleSButton;
    private JButton studentSaveButton;
    private JRadioButton radioButton1;
    private AutocompleteComboBox comboBox1;


}
