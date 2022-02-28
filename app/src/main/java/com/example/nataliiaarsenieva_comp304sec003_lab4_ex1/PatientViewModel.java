package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PatientViewModel extends AndroidViewModel {
    private PatientRepository patientRepository;
    private LiveData<List<Patient>> allPatients;

    public PatientViewModel(Application application) {
        super((application));
        patientRepository = new PatientRepository(application);
        allPatients = patientRepository.getAllPatients();
    }

    public  LiveData<Patient> findByPatientID(int patientID) {return patientRepository.findbyPatientID(patientID); }

    public void insert(Patient patient) { patientRepository.insert(patient); }

    public LiveData<List<Patient>> getAllPatientsOfNurse(int nurseID) {return patientRepository.getAllPatientsOfNurse(nurseID); }
    public LiveData<List<Patient>> getAllPatients() { return allPatients; }
    void updatePatient(int patientID, String firstName, String lastName, String room, Departments patientDepartment) { patientRepository.updatePatient(patientID, firstName, lastName, room, patientDepartment);}
}
