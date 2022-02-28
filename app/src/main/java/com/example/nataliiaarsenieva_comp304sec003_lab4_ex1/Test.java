package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "TestDB")
public class Test {
    // Initializing fields
    @PrimaryKey(autoGenerate = true)
    private int testId;
    private int patientId;
    private int nurseId;
    private int BPL;
    private int BPH;
    private double temperature;
    private double sugarLvl;

    // Test constructor
    public Test(@NonNull int BPL, @NonNull int BPH, @NonNull double temperature, @NonNull double sugarLvl, int patientId, int nurseId) {
        this.BPL = BPL;
        this.BPH = BPH;
        this.temperature = temperature;
        this.sugarLvl = sugarLvl;
        this.patientId = patientId;
        this.nurseId = nurseId; }

    // Getters & Setters
    public int getTestId() { return testId; }
    public void setTestId(int testId) { this.testId = testId; }

    public int getNurseId() { return nurseId; }
    public int getPatientId() { return patientId; }

    public int getBPH() { return BPH; }
    public void setBPH(int BPH) { this.BPH = BPH; }

    public int getBPL() { return BPL; }
    public void setBPL(int BPL) { this.BPL = BPL; }

    public double getSugarLvl() { return sugarLvl; }
    public void setSugarLvl(double sugarLvl) { this.sugarLvl = sugarLvl; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
}
