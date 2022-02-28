package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class NurseRepository {
    private final NurseDao nurseDao;
    private LiveData<List<Nurse>> nursesList;
    //
    public NurseRepository(Context context) {
        //create a database object
        NurseDatabase db = NurseDatabase.getInstance(context);
        //create an interface object
        nurseDao = db.nurseDao();
        //call interface method
        nursesList = nurseDao.getAllNurses();
    }
    // returns query results as LiveData object
    public LiveData<List<Nurse>> getAllNurses() {
        return nursesList;
    }
    //inserts a person asynchronously
    public void insert(Nurse nurse) {
        insertAsync(nurse);
    }
    // returns insert results as LiveData object

    private void insertAsync(final Nurse nurse) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                nurseDao.insert(nurse);
            }
        }).start();
    }

    public Nurse getNurse(int nurseId) { return nurseDao.getNurse(nurseId); }
    public boolean checkIfNurseExists(int nurseId) {return nurseDao.checkIfNurseExists(nurseId); }
    public String validatePassword(int nurseId) { return nurseDao.validatePassword(nurseId); }
    public Departments getDepartment(int nurseId) { return nurseDao.getDepartment(nurseId); }
}
