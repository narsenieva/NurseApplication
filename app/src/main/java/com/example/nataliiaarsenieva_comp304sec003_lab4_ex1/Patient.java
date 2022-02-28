package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PatientDB")
public class Patient {

    @PrimaryKey(autoGenerate = true)
    private int patientID;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private Departments patientDepartment;

    @NonNull
    private int nurseID;

    @NonNull
    private String room;

    public Patient(@NonNull String firstName, @NonNull String lastName, @NonNull Departments patientDepartment, @NonNull int nurseID, @NonNull String room) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patientDepartment = patientDepartment;
        this.nurseID = nurseID;
        this.room = room;
    }


    public Departments getPatientDepartment() {
        return patientDepartment;
    }

    public void setPatientID(int patientID) {this.patientID = patientID;}

    public int getPatientID() {
        return patientID;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public int getNurseID() {
        return nurseID;
    }

    @NonNull
    public String getRoom() {
        return room;
    }
}
