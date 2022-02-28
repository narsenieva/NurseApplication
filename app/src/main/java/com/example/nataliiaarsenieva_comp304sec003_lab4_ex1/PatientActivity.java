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

public class PatientActivity extends AppCompatActivity {
    // Shared Prefs
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    // TextViews - Patient Info
    private TextView loggedNurse;
    private TextView patientTitle;
    private TextView selectedPatientIndicator;
    private TextView noPatientIndicator;
    // ViewModels
    private NurseViewModel nurseViewModel;
    private PatientViewModel patientViewModel;
    // Data indicators
    public String patientInfo;
    private String selectedPatientName;
    private int selectedPatientID;
    // Buttons
    private Button updatePatient;
    private ImageButton addPatient;
    private ImageButton btnBack;
    private Button viewPatient;
    public  ArrayList<String> patientListToDisplay;
    private ListView listOfPatients;
    // Intent
    private Intent intent;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        // Setting up Shared Preferences
        prefs = getSharedPreferences("Nurse", MODE_PRIVATE);
        prefsEditor = prefs.edit();
        // Getting selected nurse
        Gson gson = new Gson();
        String json = prefs.getString("SelectedNurse", "");
        Nurse selectedNurse = gson.fromJson(json, Nurse.class);
        // Setting up fields
        loggedNurse = (TextView) findViewById(R.id.txtInfo);
        selectedPatientIndicator = (TextView) findViewById(R.id.txtPatient);
        listOfPatients = (ListView) findViewById(R.id.listOfPatients);
        noPatientIndicator = (TextView) findViewById(R.id.txtNoPatients);
        patientTitle = (TextView) findViewById(R.id.txtPatientTitle);
        updatePatient = (Button) findViewById(R.id.btnUpdateSelPatient);
        addPatient = (ImageButton) findViewById(R.id.btnAddNewPatient);
        btnBack = (ImageButton) findViewById(R.id.btnActivityPatientBack);
        viewPatient = (Button) findViewById(R.id.btnViewPatient);
        // Adding Welcome text
        loggedNurse.append(selectedNurse.getNFirstName() + "!");
        patientTitle.append(selectedNurse.getNFirstName() + " " + selectedNurse.getNLastName());
        // Setting up Array lists
        patientListToDisplay = new ArrayList<>();
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        noPatientIndicator.setVisibility(View.INVISIBLE);
        // Getting all patients of selected nurse
        patientViewModel.getAllPatientsOfNurse(selectedNurse.getNurseId()).observe(this, new Observer<List<Patient>>() {
            @Override
            public void onChanged(@Nullable List<Patient> result) {
                patientInfo = "";
                if (patientListToDisplay.isEmpty()) {
                    noPatientIndicator.setVisibility(View.VISIBLE);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.patienttextview, patientListToDisplay);
                for(Patient patient : result) {
                    noPatientIndicator.setVisibility(View.INVISIBLE);
                    patientInfo = String.format("ID: %s - %s %s %s Room: %s (%s)",
                            patient.getPatientID(),
                            patient.getFirstName(),
                            patient.getLastName(),
                            "-",
                            patient.getRoom(),
                            patient.getPatientDepartment());
                    patientListToDisplay.add(patientInfo);
                }
                listOfPatients.setAdapter(adapter);
            }
        });

        // Keeping track of selected items' ids
        listOfPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedPatientStr = listOfPatients.getItemAtPosition(i).toString();
                int start = selectedPatientStr.indexOf("-");
                int end = selectedPatientStr.indexOf("-", start +1);
                selectedPatientName = selectedPatientStr.substring(start +2, end - 1);
                selectedPatientIndicator.setText("Selected: [ " + selectedPatientName + " ]");
                selectedPatientID = Integer.parseInt(selectedPatientStr.substring(selectedPatientStr.indexOf(":") + 2, selectedPatientStr.indexOf("-") -1));
            }
        });

        // Add patient event handler
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(PatientActivity.this, AddPatient.class);
                startActivity(intent);
            }
        });

        // Update patient event handler
        updatePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPatientID != 0) {
                    prefsEditor.putInt("SelectedPatient", selectedPatientID);

                    prefsEditor.commit();
                    intent = new Intent(PatientActivity.this, UpdatePatientInfo.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PatientActivity.this, "Please pick a patient", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // View patient event handler
        viewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPatientID != 0) {
                    prefsEditor.putInt("SelectedPatient", selectedPatientID);
                    prefsEditor.putString("SelectedPatientName", selectedPatientName);
                    prefsEditor.commit();
                    intent = new Intent(PatientActivity.this, ViewPatient.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PatientActivity.this, "Please pick a patient", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Go back event handler
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(PatientActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }
}