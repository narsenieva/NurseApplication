package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ViewPatient extends AppCompatActivity {
    // Shared Prefs
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    // ViewModels
    private PatientViewModel patientViewModel;
    private TestViewModel testViewModel;
    // TextViews - Patient Info
    private TextView txtPatientID;
    private TextView txtNurseName;
    private TextView txtFullName;
    private TextView txtDepartment;
    private TextView txtRoom;
    // Data Indicators
    private String testInfo;
    private String fullName;
    private int selectedtestID;
    // TextView Titles
    private TextView lblInfoPatient;
    private TextView lblTest;
    // TextView Indicators
    private TextView noTestIndicator;
    private TextView selectedTestIndicator;
    // Buttons
    private ImageButton btnBack;
    private ImageButton addNewTest;
    private Button updateSelTest;
    private Button viewSelTest;
    // List of Tests
    public ArrayList<String> testListToDisplay;
    private ListView listOfTests;
    // Intent
    private Intent intent;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        // Get information from Shared Preferences
        prefs = getSharedPreferences("Nurse", MODE_PRIVATE);
        prefsEditor = prefs.edit();
        int id = prefs.getInt("SelectedPatient", 0);
        String patientName = prefs.getString("SelectedPatientName", "");
        Gson gson = new Gson();
        String json = prefs.getString("SelectedNurse", "");
        Nurse selectedNurse = gson.fromJson(json, Nurse.class);
        // Initializing Patient text fields
        txtPatientID = (TextView)findViewById(R.id.txtViewPatientID);
        txtNurseName = (TextView)findViewById(R.id.txtViewPNurse);
        txtFullName = (TextView)findViewById(R.id.txtViewPFullName);
        txtDepartment = (TextView)findViewById(R.id.txtViewPDepartment);
        txtRoom = (TextView)findViewById(R.id.txtViewPRoom);
        // Initializing Indicators fields
        noTestIndicator = (TextView) findViewById(R.id.txtNoTests);
        selectedTestIndicator = (TextView) findViewById(R.id.txtSelectedTestIndicator);
        // Initializing Buttons
        btnBack = (ImageButton) findViewById(R.id.btnViewPatientBack);
        addNewTest = (ImageButton) findViewById(R.id.btnAddNewTest);
        updateSelTest = (Button) findViewById(R.id.btnUpdateSelTest);
        viewSelTest = (Button) findViewById(R.id.btnViewTest);
        // Initializing ViewModels
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        // Initializing Lists
        listOfTests = (ListView) findViewById(R.id.listOfTests);
        testListToDisplay = new ArrayList<>();
        // Initializing Titles
        lblInfoPatient = (TextView) findViewById(R.id.txtInfoPatient);
        lblTest = (TextView) findViewById(R.id.txtTestTitle);

        noTestIndicator.setVisibility(View.INVISIBLE); // Setting no tests indicator to invisible by default

        // Getting patient's information
        patientViewModel.findByPatientID(id).observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(@Nullable Patient result) {
                fullName = result.getFirstName() + " " + result.getLastName();
                lblInfoPatient.setText(fullName);
                lblTest.append(" " + fullName);
                txtPatientID.setText(String.valueOf(result.getPatientID()));
                txtFullName.setText(fullName);
                txtDepartment.setText(result.getPatientDepartment().name());
                txtRoom.setText(result.getRoom());
                txtNurseName.setText(selectedNurse.getNFirstName() + " " + selectedNurse.getNLastName());
            }
        });

        // Getting all the tests of selected patient
        testViewModel.getAllTestsOfPatient(id).observe(this, new Observer<List<Test>>() {
            @Override
            public void onChanged(@Nullable List<Test> result) {
                testInfo = "";
                // Show no tests indicator if the list is empty
                if (testListToDisplay.isEmpty()) { noTestIndicator.setVisibility(View.VISIBLE); }
                // Creating an array adapter for the listview
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.testtextview, testListToDisplay);
                // Looping through all tests and adding them to the list
                for (Test test : result) {
                    noTestIndicator.setVisibility(View.INVISIBLE);
                    testInfo = String.format("%s  -  %5s  %8s %8s %8s %8s",
                            test.getTestId(),
                            patientName,
                            test.getTemperature(),
                            test.getBPH(), test.getBPL(),
                            test.getSugarLvl());
                    testListToDisplay.add(testInfo); }
                // Setting an adapter to the listview
                listOfTests.setAdapter(adapter);
            }
        });

        // Keeping track of selected items' ids
        listOfTests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedTestStr = listOfTests.getItemAtPosition(i).toString();
                selectedtestID = Integer.parseInt(selectedTestStr.substring(0, selectedTestStr.indexOf("-")-2));
                selectedTestIndicator.setText("Selected: [ Test #" + selectedtestID + " ]");
            }
        });

        // Add new test event handler
        addNewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ViewPatient.this, AddTest.class);
                startActivity(intent);
            }
        });

        // Update test event handler
        updateSelTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedtestID != 0) { // If a test was selected
                    // Pass id to shared prefs
                    prefsEditor.putInt("SelectedTest", selectedtestID);
                    prefsEditor.commit();
                    // Start activity
                    intent = new Intent(ViewPatient.this, UpdateTestInfo.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ViewPatient.this, "Please pick a test", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // View test event handler
        viewSelTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedtestID != 0) { // If a test was selected
                    // Pass id to shared prefs
                    prefsEditor.putInt("SelectedTest", selectedtestID);
                    prefsEditor.commit();
                    // Start activity
                    intent = new Intent(ViewPatient.this, ViewTest.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ViewPatient.this, "Please pick a test", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Go back event handler
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ViewPatient.this, PatientActivity.class);
                startActivity(intent);
            }
        });
    }
}