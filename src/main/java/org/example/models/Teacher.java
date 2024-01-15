package org.example.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Teacher")
@Entity
public class Teacher {
    @Id
    @Column
    @GeneratedValue
    int IDgnorable;

    @Column(length = 100)
    String teachFirstName;
    @Column(length = 100)
    String teachLastName;

    @Column
    int hour1;
    @Column
    int hour2;
    @Column
    int hour3;
    @Column
    int hour4;
    @Column
    int hour5;
    @Column
    int hour6;
    @Column
    int hour7;
    @Column
    int hour8;

    public Teacher(String teachFirstName, String teachLastName) {
        this.teachFirstName=teachFirstName;
        this.teachLastName=teachLastName;
        hour1=0;
        hour2=0;
        hour3=0;
        hour4=0;
        hour5=0;
        hour6=0;
        hour7=0;
        hour8=0;
    }

    public String toString(){
        return this.teachFirstName+" "+this.teachLastName;
    }
}
