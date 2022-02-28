package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PatientRepository {

    public PatientDao patientDao;
    private LiveData<List<Patient>> allPatients;

    public PatientRepository(Application application)
    {
        PatientDatabase db = PatientDatabase.getDatabase(application);
        patientDao = db.patientDao();
        allPatients = patientDao.getAllPatients();
    }

    public LiveData<List<Patient>> getAllPatients() {return allPatients; }

    public void insert(Patient patient) {
        PatientDatabase.databaseWriteExecutor.execute(() -> {
            patientDao.insert(patient);
        });
    }

    public LiveData<List<Patient>> getAllPatientsOfNurse(int nurseID) {return patientDao.getAllPatientsOfNurse(nurseID); }
    public LiveData<Patient> findbyPatientID(int patientID) {return patientDao.getByPatientID(patientID); }
    void updatePatient(int patientID, String firstName, String lastName, String room, Departments patientDepartment) { patientDao.updatePatient(patientID, firstName, lastName, room, patientDepartment);}
}
