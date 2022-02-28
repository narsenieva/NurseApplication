package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TestDao {

    @Insert
    void insert(Test test);

    @Update
    void update(Test test);

    @Delete
    void delete(Test test);

    @Query("DELETE FROM TestDB")
    void deleteAll();

    @Query("Select * FROM TestDB where testId = :testID")
    LiveData<Test> getTestByID(int testID);

    @Query("Select * FROM TestDB")
    LiveData<List<Test>> getAllTests();

    @Query("Update TestDB set BPL = :BPL, BPH = :BPH, temperature = :temperature, sugarLvl = :sugarLvl  where testId = :testID")
    void updateTest(int testID, int BPH, int BPL, double temperature, double sugarLvl);

    @Query("Select * FROM TestDB where patientId = :patientID")
    LiveData<List<Test>> getAllTestsOfPatient(int patientID);
}
