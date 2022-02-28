package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class NurseViewModel extends AndroidViewModel {
    // calling repository tasks and
    // sending the results to the Activity
    private NurseRepository nurseRepository;
    private LiveData<List<Nurse>> allNurses;
    //
    public NurseViewModel(@NonNull Application application) {
        super(application);
        nurseRepository = new NurseRepository(application);
        allNurses = nurseRepository.getAllNurses();
    }
    //calls repository to insert a person
    public void insert(Nurse nurse) {
        nurseRepository.insert(nurse);
    }
    //returns query results as live data object
    public LiveData<List<Nurse>> getAllNurses() { return allNurses; }

    public Nurse getNurse(int nurseId) { return nurseRepository.getNurse(nurseId); }
    public boolean checkIfNurseExists(int nurseID) { return nurseRepository.checkIfNurseExists(nurseID); }
    public String validatePassword(int nurseId) { return nurseRepository.validatePassword(nurseId); }
    public Departments getDepartment(int nurseId) { return nurseRepository.getDepartment(nurseId); }

}
