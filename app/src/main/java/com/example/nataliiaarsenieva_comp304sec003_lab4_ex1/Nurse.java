package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

enum Departments {
    Dentistry,
    Nursing,
    Surgery,
    Psychiatry
}

@Entity(tableName = "NurseDB")
public class Nurse {

    @PrimaryKey(autoGenerate = true)
    private int nurseId;

    private String nFirstName;
    private String nLastName;
    private Departments department;
    private String password;


    public Nurse(@NonNull int id, @NonNull String firstName, @NonNull String lastName, @NonNull String password, @NonNull Departments department) {
        this.nFirstName = firstName;
        this.department = department;
        this.nLastName = lastName;
        this.nurseId = id;
        this.password = password;
    }


    public Departments getDepartment() { return department; }
    public void setDepartment(Departments department) { this.department = department; }

    public int getNurseId() {
        return nurseId;
    }
    public void setNurseId(int nurseId) { this.nurseId = nurseId; }

    public String getNFirstName() { return nFirstName; }
    public void setNFirstName(String nFirstName) {
        this.nFirstName = nFirstName;
    }

    public String getNLastName() {
        return nLastName;
    }
    public void setNLastName(String nLastName) {
        this.nLastName = nLastName;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }

    public Nurse(String nFirstName) {
        this.nFirstName = nFirstName;
    }
}
