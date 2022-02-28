package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TestRepository {

    public TestDao testDao;
    private LiveData<List<Test>> allTests;

    public TestRepository(Application application)
    {
        TestDatabase db = TestDatabase.getDatabase(application);
        testDao = db.testDao();
        allTests = testDao.getAllTests();
    }

    public LiveData<List<Test>> getAllTests() {return allTests; }

    public void insert(Test test) {
        PatientDatabase.databaseWriteExecutor.execute(() -> {
            testDao.insert(test);
        });
    }

    public LiveData<List<Test>> getAllTestsOfPatient(int patientID) {return testDao.getAllTestsOfPatient(patientID); }
    public LiveData<Test> getTestByID(int testID) {return testDao.getTestByID(testID); }
    void updateTest(int testID, int BPH, int BPL, double temperature, double sugarLvl) {
        testDao.updateTest(testID, BPH, BPL, temperature, sugarLvl);}
}
