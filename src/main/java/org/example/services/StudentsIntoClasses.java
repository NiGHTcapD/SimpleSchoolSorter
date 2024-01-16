package org.example.services;

import org.example.models.Student;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class StudentsIntoClasses {
    public static final int MAX_ATTEMPTS = 8;
    ///a nd et c. All the results will be joined in function, but it only asks for unfilled hours
    //if it fails to return a successful match, it may need to be rerandomized in its order of operations
    //To cut down on the randomness, perhaps that function runs for all 8 courses and looks for the class with the least choices
    //selects randomly from that list
    //Run it for 7 remaining courses across 7 remaining hours
    //et cetera
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    public int[] studentScheduler(int id) {
        //[0,0,0,0,0,0,0,0]
        int[] classesScheduled = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        //make a list of classes needed

        //Loop for 8 iterations with a break condition
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            //to future generations: Be careful if you go in here.
            classesScheduled = oneAttemptToScheduleAStudent(id, classesScheduled);
            if (classesScheduled == null) break;
        }
        if (classesScheduled != null){studentRepository.findStudentByID(id).setFlag(true);}
        return classesScheduled;
    }




    private int[] oneAttemptToScheduleAStudent(int id, int[] classesScheduled) {

        //Okay. If you're gonna poke around in here, I want you to understand what you're looking at.
        //Do you know those logic puzzles where you have a grid of five-by-five, sometimes multiple stitched together, and one thing only goes to one thing?
        //This is that, with one grid of 8 by 8. The class to schedule, and the hour it's in. With theoretically multiple possibilities and mostly-complete information.
        // (Technically, it's 7 by 7 and 1 by 1, as the original users have 7 hours and an advisory period that interfaces/interferes with lunch.)
        //This program needs to juggle both, and take the class and period out of the pool when the simple algorithm matches class to period.
        //The algorithm? Finds every possible combination of open class and open period, avoiding those that have been "crossed out", and takes the class with least options...
        //...and randomly puts one of them into practice. (You need the randomness. Can't have two kids with exactly the same schedule if you can help it.)
        //In theory, this should shake out to a chain reaction where the pieces that must be placed where they area cause other pieces to fall into place, but if not...
        //That's why this gets run multiple times.

        //Most of the "nonsense" and juggling happens in fillClassesCouldSchedule.

        //call student by id and get their class list in an array ;
        int[] studentClassList = findStudentsClasses(id);
        for (int hourToFill = 1; hourToFill <= 8; hourToFill++) {
            //  Loop all open hours for each class

            List<Pair<Integer, Integer>>[] classesCouldSchedule = new List[8];
            for (int i = 0; i<8; i++){
                classesCouldSchedule[i]=new ArrayList<>();
            }
            fillClassesCouldSchedule(classesScheduled, studentClassList, classesCouldSchedule);
            //  The lowest nonzero count for class...
            int isLowest = getLowest(classesCouldSchedule);
            //    gets a teacher-classtime randomly selected for them
            scheduleClass(classesScheduled, studentClassList, classesCouldSchedule, isLowest);
        }
        //  If no nonzeroes occur, too early, then you've screwed up somewhere. Run it again, up to 8 times.
        //if run again...classesScheduled = {0, 0, 0, 0, 0, 0, 0, 0};
        if (findZero(classesScheduled)){
            classesScheduled = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        }
        else{
            return null;
        }
        return classesScheduled;
    }

    private static void scheduleClass(int[] classesScheduled, int[] studentClassList, List<Pair<Integer, Integer>>[] classesCouldSchedule, int isLowest) {
        if (classesCouldSchedule[isLowest].size()>0) {//this handles an error on the next line where 0 is not a positive bound for randomization
            int randomed = new Random().nextInt(classesCouldSchedule[isLowest].size());
            Pair<Integer, Integer> classToScheduleByID = classesCouldSchedule[isLowest].get(randomed);
            //  Pop the class (or put a 0 on that part of the list) and put the teacher-class ID in the 8-int list
            studentClassList[isLowest] = 0;
            //int hourScheduledIn = the hour of classToScheduleByID
            int hourScheduledIn = classesCouldSchedule[isLowest].get(randomed).getFirst();
            classesScheduled[hourScheduledIn] = classToScheduleByID.getSecond();
        }
    }

    static int getLowest(List<Pair<Integer, Integer>>[] classesCouldSchedule) {
        int lowest = 0;
        int isLowest = 0;
        for (int i = 0; i< classesCouldSchedule.length; i++) {
            if (lowest == 0){
                lowest = classesCouldSchedule[i].size();
                isLowest=i;
            }
            else if (lowest> classesCouldSchedule[i].size() && classesCouldSchedule[i].size()>0){
                lowest = classesCouldSchedule[i].size();
                isLowest=i;
            }
        }
        return isLowest;
    }

    private void fillClassesCouldSchedule(int[] classesScheduled, int[] studentClassList, List<Pair<Integer, Integer>>[] classesCouldSchedule) {
        for (int course = 0; course <= 7; course++) {
            //    check only hours without 0/null for all classes of that ID
            //for each unused class...
            if (studentClassList[course] != 0) {
                for (int hour = 0; hour <= 7; hour++) {
                    if (classesScheduled[hour] != 0) {
                        int finalHour = hour;
                        List<Pair<Integer, Integer>> classesToList = findByHourX(hour, studentClassList[course]).stream().map(foundClass -> Pair.of(foundClass, finalHour)).toList();
                        classesCouldSchedule[course].addAll((Collection<? extends Pair<Integer, Integer>>) classesToList);
                    }
                }
            }
        }
    }

    boolean findZero(int[] array) {
        for (int element : array) {
            if (element == 0) {
                return true;
            }
        }
        return false;
    }

    private int[] findStudentsClasses(int id) {
        Student stud = studentRepository.findStudentByID(id);
        int[] classIDs = new int[8];
        classIDs[0] = stud.getClass1();
        classIDs[1] = stud.getClass2();
        classIDs[2] = stud.getClass3();
        classIDs[3] = stud.getClass4();
        classIDs[4] = stud.getClass5();
        classIDs[5] = stud.getClass6();
        classIDs[6] = stud.getClass7();
        classIDs[7] = stud.getClass8();
        return classIDs;
    }

    List<Integer> findByHourX(int hour, int classID) {
        return switch (hour) {
            case 1 -> teacherRepository.findByHour1(classID);
            case 2 -> teacherRepository.findByHour2(classID);
            case 3 -> teacherRepository.findByHour3(classID);
            case 4 -> teacherRepository.findByHour4(classID);
            case 5 -> teacherRepository.findByHour5(classID);
            case 6 -> teacherRepository.findByHour6(classID);
            case 7 -> teacherRepository.findByHour7(classID);
            case 8 -> teacherRepository.findByHour8(classID);
            default -> null;
        };
    }
}
