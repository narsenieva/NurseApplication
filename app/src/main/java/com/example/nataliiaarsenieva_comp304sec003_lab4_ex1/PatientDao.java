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
public interface PatientDao {

    @Insert
    void insert(Patient patient);

    @Update
    void update(Patient patient);

    @Delete
    void delete(Patient patient);

    @Query("DELETE FROM PatientDB")
    void deleteAll();

    @Query("Select * FROM PatientDB where patientID = :patientID")
    LiveData<Patient> getByPatientID(int patientID);

    @Query("Select * FROM PatientDB")
    LiveData<List<Patient>> getAllPatients();

    @Query("Update PatientDB set firstName = :firstName, lastName = :lastName, room = :room, patientDepartment = :patientDepartment where patientID = :patientID")
    void updatePatient(int patientID, String firstName, String lastName, String room, Departments patientDepartment);

    @Query("Select * FROM PatientDB where nurseID = :nurseID")
    LiveData<List<Patient>> getAllPatientsOfNurse(int nurseID);

}
