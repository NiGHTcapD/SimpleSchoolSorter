package org.example.repository;

import org.example.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    @Query("select teach.IDgnorable from Teacher teach where teach.hour1 = :classID")
    List<Integer> findByHour1(@Param("classID") int classID);

    @Query("select teach.IDgnorable from Teacher teach where teach.hour2 = :classID")
    List<Integer> findByHour2(@Param("classID") int classID);

    @Query("select teach.IDgnorable from Teacher teach where teach.hour3 = :classID")
    List<Integer> findByHour3(@Param("classID") int classID);

    @Query("select teach.IDgnorable from Teacher teach where teach.hour4 = :classID")
    List<Integer> findByHour4(@Param("classID") int classID);

    @Query("select teach.IDgnorable from Teacher teach where teach.hour5 = :classID")
    List<Integer> findByHour5(@Param("classID") int classID);

    @Query("select teach.IDgnorable from Teacher teach where teach.hour6 = :classID")
    List<Integer> findByHour6(@Param("classID") int classID);

    @Query("select teach.IDgnorable from Teacher teach where teach.hour7 = :classID")
    List<Integer> findByHour7(@Param("classID") int classID);

    @Query("select teach.IDgnorable from Teacher teach where teach.hour8 = :classID")
    List<Integer> findByHour8(@Param("classID") int classID);



    @Query("select teach.IDgnorable from Teacher teach where teach.teachFirstName = :firstName")
    Teacher findTeacherByName(@Param("firstName") String firstName);

    @Query("select teach from Teacher teach where teach.IDgnorable = :teacherID")
    Teacher findTeacherByID(@Param("teacherID") int teacherID);
}
