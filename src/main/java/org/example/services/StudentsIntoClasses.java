package org.example.services;

import org.example.models.Student;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StudentsIntoClasses {
    public static final int MAX_ATTEMPTS = 8;
    public static final int MAX_CLASSES = 8;
    private static final int[] EMPTY_ARRAY = new int[MAX_CLASSES];
    ///a nd et c. All the results will be joined in function, but it only asks for unfilled hours
    //if it fails to return a successful match, it may need to be rerandomized in its order of operations
    //To cut down on the randomness, perhaps that function runs for all 8 courses and looks for the class with the least choices
    //selects randomly from that list
    //Run it for 7 remaining courses across 7 remaining hours
    //et cetera
    int[] classesScheduled;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;


    public Pair<Integer, Integer>[] studentScheduler(Student student) {
        classesScheduled = new int[MAX_CLASSES];

        //Loop for 8 iterations with a break condition
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {

            //to future generations: Be careful if you go in here.
            oneAttemptToScheduleAStudent(student, classesScheduled);
            if (!Arrays.equals(classesScheduled, EMPTY_ARRAY)) {
                student.setFlag(true);
                studentRepository.save(student);///DOES NOT DO WHAT IT SAYS ON THE TIN!!!!!!!!!!
                //Classes scheduled currently consists of the teacher IDs, not the class IDs! Fix it by generating a new list from it?
                // But keep the teacher IDs. You may have to expand our the contents of what student contains.
                Pair<Integer, Integer>[] classesAndTeachersScheduled = alterator(classesScheduled);//Turn techer ints into Pairs(Integer,Integer)!
                return classesAndTeachersScheduled;
                //break;
            }
        }

        System.out.println(java.util.Arrays.toString(classesScheduled));
        return new Pair<>[8];
    }

    private Pair<Integer, Integer>[] alterator(int[] classesScheduled) {
        Pair<Integer, Integer>[] classesAndTeachers = new Pair[8];
        int[] teacherOrder = new int[MAX_CLASSES];
        classesAndTeachers[0] = Pair.of(teacherRepository.findTeacherByID(classesScheduled[0]).getHour1(), classesScheduled[0]);
        classesAndTeachers[1] = Pair.of(teacherRepository.findTeacherByID(classesScheduled[1]).getHour2(), classesScheduled[1]);
        classesAndTeachers[2] = Pair.of(teacherRepository.findTeacherByID(classesScheduled[2]).getHour3(), classesScheduled[2]);
        classesAndTeachers[3] = Pair.of(teacherRepository.findTeacherByID(classesScheduled[3]).getHour4(), classesScheduled[3]);
        classesAndTeachers[4] = Pair.of(teacherRepository.findTeacherByID(classesScheduled[4]).getHour5(), classesScheduled[4]);
        classesAndTeachers[5] = Pair.of(teacherRepository.findTeacherByID(classesScheduled[5]).getHour6(), classesScheduled[5]);
        classesAndTeachers[6] = Pair.of(teacherRepository.findTeacherByID(classesScheduled[6]).getHour7(), classesScheduled[6]);
        classesAndTeachers[7] = Pair.of(teacherRepository.findTeacherByID(classesScheduled[7]).getHour8(), classesScheduled[7]);

        return classesAndTeachers;
    }


    private void oneAttemptToScheduleAStudent(Student student, int[] classesScheduled) {

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

        //make a list of classes needed
        //call student by id and get their class list in an array ;
        int[] studentClassList = findStudentsClasses(student);
        for (int hourToFill = 0; hourToFill < MAX_CLASSES; hourToFill++) {
            //  Loop all open hours for each class

            List<Pair<Integer, Integer>>[] classesCouldSchedule = fillClassesCouldSchedule(classesScheduled, studentClassList);
            //  The lowest nonzero count for class...
            int isLowest = getLowest(classesCouldSchedule);
            //    gets a teacher-classtime randomly selected for them
            scheduleClass(classesScheduled, studentClassList, classesCouldSchedule, isLowest);
        }

        System.out.println(java.util.Arrays.toString(classesScheduled) + "please");
        //  If no nonzeroes occur, too early, then you've screwed up somewhere. Run it again, up to 8 times.
        //if run again...classesScheduled = {0, 0, 0, 0, 0, 0, 0, 0};
        if (findZero(classesScheduled)) {
            System.arraycopy(EMPTY_ARRAY,0,classesScheduled,0, classesScheduled.length);
        }
    }

    private void scheduleClass(int[] classesScheduled, int[] studentClassList, List<Pair<Integer, Integer>>[] classesCouldSchedule, int isLowest) {
        if (classesCouldSchedule[isLowest].size() > 0) {//this handles an error on the next line where 0 is not a positive bound for randomization

            //[client] wants to weight this based on what classes have more students scheduled
            int randomed = weightClassesCouldSchedule(classesCouldSchedule[isLowest]);

            //int randomed = new Random().nextInt(classesCouldSchedule[isLowest].size());
            Pair<Integer, Integer> classToScheduleByID = classesCouldSchedule[isLowest].get(randomed);
            //  Pop the class (or put a 0 on that part of the list) and put the teacher-class ID in the 8-int list
            studentClassList[isLowest] = 0;
            //first is hour, second should be the course ID
            classesScheduled[classToScheduleByID.getFirst()] = classToScheduleByID.getSecond();
        }
    }

    private int weightClassesCouldSchedule(List<Pair<Integer, Integer>> pairs) {
        //apply weights as a negative distribution. With no students taking that particular class at that particular time? say...5/1 weight.
        //1 student already taking, 5/2. Following this, 5/n+1

        //get all students taking the course with that teacher, in that hour
        //and do so for all classes
        //apply to them the weight based on that number
        //add that up into totalWeight
        //then loose it upon the code below

        //perhaps the input should be simpler

        //SQL query: return count of students with this class, this period

        List<Double> items= new ArrayList<>();
        AtomicReference<Double> totalWeight= new AtomicReference<>((double) 0);
        pairs.forEach(pair->{
            switch (pair.getFirst()) {
                case 1 -> items.add((double)1/studentRepository.findClassAllocation1(pair.getSecond()));
                case 2 -> items.add((double)1/studentRepository.findClassAllocation2(pair.getSecond()));
                case 3 -> items.add((double)1/studentRepository.findClassAllocation3(pair.getSecond()));
                case 4 -> items.add((double)1/studentRepository.findClassAllocation4(pair.getSecond()));
                case 5 -> items.add((double)1/studentRepository.findClassAllocation5(pair.getSecond()));
                case 6 -> items.add((double)1/studentRepository.findClassAllocation6(pair.getSecond()));
                case 7 -> items.add((double)1/studentRepository.findClassAllocation7(pair.getSecond()));
                case 8 -> items.add((double)1/studentRepository.findClassAllocation8(pair.getSecond()));
            }
            if (!items.isEmpty()){
                totalWeight.updateAndGet(v -> v + items.get(items.size()-1));
            }
        });

        int idx = 0;
        for (double randomizerTally = Math.random() * totalWeight.get(); idx < items.size() - 1; ++idx) {
            randomizerTally -= items.get(idx);
            if (randomizerTally <= 0.0) break;
        }

        return idx;
    }

    static int getLowest(List<Pair<Integer, Integer>>[] classesCouldSchedule) {//get the lowest nonzero number. These numbers are tallies of valid classcounts.
        int lowest = 0;
        int isLowest = 0;
        for (int i = 0; i < classesCouldSchedule.length; i++) {
            if (lowest == 0) {
                lowest = classesCouldSchedule[i].size();
                isLowest = i;
            } else if (lowest > classesCouldSchedule[i].size() && classesCouldSchedule[i].size() > 0) {
                lowest = classesCouldSchedule[i].size();
                isLowest = i;
            }
        }
        return isLowest;
    }

    private List<Pair<Integer, Integer>>[] fillClassesCouldSchedule(int[] classesScheduled, int[] studentClassList) {
        List<Pair<Integer, Integer>>[] classesCouldSchedule = new List[MAX_CLASSES];
        for (int i = 0; i < MAX_CLASSES; i++) {
            classesCouldSchedule[i] = new ArrayList<>();
        }

        for (int course = 0; course < MAX_CLASSES; course++) {
            //    check only hours without 0/null for all classes of that ID
            //for each unused class...
            if (studentClassList[course] != 0) {
                for (int hour = 0; hour < MAX_CLASSES; hour++) {
                    if (classesScheduled[hour] == 0) {//if the number of classes that could be schedules for that hour is not 0
                        int finalHour = hour;
                        List<Pair<Integer, Integer>> classesToList = findByHourX(hour, studentClassList[course]).stream().map(foundClass -> Pair.of(finalHour, foundClass)).toList();
                        classesCouldSchedule[course].addAll(classesToList);
                    }
                }
            }//classesCouldSchedule is a list of pairs where the hour is first and the class is second
        }

        return classesCouldSchedule;
    }

    boolean findZero(int[] array) {
        for (int element : array) {
            if (element == 0) {
                return true;
            }
        }
        return false;
    }

    private int[] findStudentsClasses(Student student) {
        int[] classIDs = new int[MAX_CLASSES];
        classIDs[0] = student.getClass1();
        classIDs[1] = student.getClass2();
        classIDs[2] = student.getClass3();
        classIDs[3] = student.getClass4();
        classIDs[4] = student.getClass5();
        classIDs[5] = student.getClass6();
        classIDs[6] = student.getClass7();
        classIDs[7] = student.getClass8();
        return classIDs;
    }

    List<Integer> findByHourX(int hour, int classID) {
        return switch (hour) {
            case 0 -> teacherRepository.findByHour1(classID);
            case 1 -> teacherRepository.findByHour2(classID);
            case 2 -> teacherRepository.findByHour3(classID);
            case 3 -> teacherRepository.findByHour4(classID);
            case 4 -> teacherRepository.findByHour5(classID);
            case 5 -> teacherRepository.findByHour6(classID);
            case 6 -> teacherRepository.findByHour7(classID);
            case 7 -> teacherRepository.findByHour8(classID);
            default -> Collections.emptyList();
        };
    }
}
