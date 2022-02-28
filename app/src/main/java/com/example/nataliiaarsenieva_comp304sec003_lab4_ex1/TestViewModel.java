package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TestViewModel extends AndroidViewModel {
    private TestRepository testRepository;
    private LiveData<List<Test>> allTests;

    public TestViewModel(Application application) {
        super((application));
        testRepository = new TestRepository(application);
        allTests = testRepository.getAllTests();
    }

    public  LiveData<Test> getTestByID(int testID) {return testRepository.getTestByID(testID); }

    public void insert(Test patient) { testRepository.insert(patient); }

    public LiveData<List<Test>> getAllTestsOfPatient(int patientID) {return testRepository.getAllTestsOfPatient(patientID); }
    public LiveData<List<Test>> getAllPatients() { return allTests; }
    void updateTest(int testID, int BPH, int BPL, double temperature, double sugarLvl) {
        testRepository.updateTest(testID, BPH, BPL, temperature, sugarLvl);}
}
