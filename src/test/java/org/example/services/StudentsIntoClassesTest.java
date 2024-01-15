package org.example.services;

import org.springframework.data.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentsIntoClassesTest {

    StudentsIntoClasses studentsIntoClasses = new StudentsIntoClasses();

    @Test
    void getLowest() {
        List<Pair<Integer, Integer>>[] proveThis = new List[8];
        proveThis[1] = Arrays.asList(Pair.of(12, 312), Pair.of(1, 32));
        proveThis[3] = List.of(Pair.of(12, 312));
        proveThis[4] = Arrays.asList(Pair.of(12, 312), Pair.of(1, 32));
        proveThis[6] = Arrays.asList(Pair.of(12, 312), Pair.of(1, 32));
        proveThis[7] = List.of(Pair.of(1, 32));
        proveThis[0] = new ArrayList<>();
        proveThis[2] = new ArrayList<>();
        proveThis[5] = new ArrayList<>();

        int proveThat = StudentsIntoClasses.getLowest(proveThis);

        assertThat(3).isEqualTo(proveThat);
    }

    @Test
    void findZero(){
        int[] classIdsZero = new int[]{235, 62345, 2340, 921346, 99999, 21474, 0, 2};
        assertThat(studentsIntoClasses.findZero(classIdsZero)).isTrue();
        int[] classIdsNoZero = new int[]{1342, 5135, 353, 25354, 543265787};
        assertThat(studentsIntoClasses.findZero(classIdsNoZero)).isFalse();
    }


}