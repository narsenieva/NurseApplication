package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NurseDao {

    @Query("SELECT * FROM NurseDB")
    LiveData<List<Nurse>> getAllNurses();
    //

    @Query("DELETE FROM NurseDB")
    void deleteAll();

    @Query("SELECT * FROM NurseDB where nurseId = :nurseId")
    Nurse getNurse(int nurseId);

    @Query("Select EXISTS (Select * FROM NurseDB where nurseId = :nurseId)")
    boolean checkIfNurseExists(int nurseId);

    @Query("Select password from NurseDB where nurseId = :nurseId")
    String validatePassword(int nurseId);

    @Query("Select department from NurseDB where nurseId = :nurseId")
    Departments getDepartment(int nurseId);


    @Insert
    void insert(Nurse nurse);
    //Monitoring Query Result Changes with Live Data
}
