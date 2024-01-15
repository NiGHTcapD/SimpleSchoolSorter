package org.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Course")
@Entity
public class Course {
    @Id
    @Column
    @GeneratedValue
    private int classId;

    @Column(length = 100)
    private String courseName;

    @Column
    private int courseGrade;

    public Course(String courseName, int courseGrade) {
        this.courseName=courseName;
        this.courseGrade=courseGrade;
    }

    public String toString(){
        return this.courseName;
    }

    public int getClassId() {
        return classId;
    }
}