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
@Table(name = "Student")
@Entity
public class Student {
    @Id
    @Column
    @GeneratedValue
    int IDgnorable;

    @Column(length = 100)
    String studentFirstName;
    @Column(length = 100)
    String studentLastName;

    @Column
    int studentGrade;

    @Column
    int class1;
    @Column
    int class2;
    @Column
    int class3;
    @Column
    int class4;
    @Column
    int class5;
    @Column
    int class6;
    @Column
    int class7;
    @Column
    int class8;

    @Column
    int teacher1;
    @Column
    int teacher2;
    @Column
    int teacher3;
    @Column
    int teacher4;
    @Column
    int teacher5;
    @Column
    int teacher6;
    @Column
    int teacher7;
    @Column
    int teacher8;

    @Column
    boolean flag;

    public Student(String studentFirstName, String studentLastName, int studentGrade) {
        this.studentFirstName=studentFirstName;
        this.studentLastName=studentLastName;
        this.studentGrade=studentGrade;
        class1=0;
        class2=0;
        class3=0;
        class4=0;
        class5=0;
        class6=0;
        class7=0;
        class8=0;
        teacher1=0;
        teacher2=0;
        teacher3=0;
        teacher4=0;
        teacher5=0;
        teacher6=0;
        teacher7=0;
        teacher8=0;
        flag=false;
    }

    public String toString(){
        return this.studentFirstName+" "+this.studentLastName;// +"["+class1+class2+class3+class4+class5+class6+class7+class8+"]";
    }

    public int getIDgnorableS(){
        return IDgnorable;
    }
}
