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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

public class UpdateTestInfo extends AppCompatActivity {
    // Shared Prefs
    SharedPreferences prefs;
    // ViewModels
    private PatientViewModel patientViewModel;
    private TestViewModel testViewModel;
    // TextViews - Patient and Nurse Info
    private EditText txtNurseId;
    private EditText txtPFullName;
    // TextViews - Test Info
    private EditText txtTemp;
    private EditText txtBPL;
    private EditText txtBPH;
    private EditText txtSugarLVL;
    // Buttons
    private Button btnUpdate;
    private ImageButton btnBack;
    // Intent
    private Intent intent;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_test_info);
        // Get information from Shared Preferences
        prefs = getSharedPreferences("Nurse", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("SelectedNurse", "");
        Nurse selectedNurse = gson.fromJson(json, Nurse.class);
        int patientid = prefs.getInt("SelectedPatient", 0);
        int testid = prefs.getInt("SelectedTest", 0);
        // Initializing ViewModels
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        // Initializing Buttons
        btnUpdate = (Button) findViewById(R.id.btnUpdTest);
        btnBack = (ImageButton) findViewById(R.id.btnUpdateTestBack);
        // Initializing Patient and Nurse text fields
        txtNurseId = (EditText) findViewById(R.id.txtTestUpdPNurseID);
        txtPFullName = (EditText) findViewById(R.id.txtTestUpdPFullName);
        // Initializing Test text fields
        txtTemp = (EditText) findViewById(R.id.txtTestUpdPTemperature);
        txtBPH = (EditText) findViewById(R.id.txtTestUpdPBPH);
        txtBPL = (EditText) findViewById(R.id.txtTestUpdPBPL);
        txtSugarLVL = (EditText) findViewById(R.id.txtTestUpdPSugarLVL);
        // Disabled Patient and Nurse fields for any input
        txtNurseId.setEnabled(false);
        txtPFullName.setEnabled(false);
        // Set nurse ID
        txtNurseId.setText(String.valueOf(selectedNurse.getNurseId()));
        // Getting patient's information
        patientViewModel.findByPatientID(patientid).observe(this, new Observer<Patient>() { @Override
        public void onChanged(@Nullable Patient result) { txtPFullName.setText(result.getFirstName() + " " + result.getLastName()); }});

        // Getting selected test information
        testViewModel.getTestByID(testid).observe(this, new Observer<Test>() {
            @Override
            public void onChanged(@Nullable Test result) {
                // Set values as text
                txtTemp.setText(String.valueOf(result.getTemperature()));
                txtBPH.setText(String.valueOf(result.getBPH()));
                txtBPL.setText(String.valueOf(result.getBPL()));
                txtSugarLVL.setText(String.valueOf(result.getSugarLvl()));
            }
        });

        // Update test event handler
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInfo()) { // Get all text for further examination
                    if (txtTemp.getText().toString().length() != 0 && txtBPH.getText().toString().length() != 0
                            && txtBPL.getText().toString().length() != 0 && txtSugarLVL.getText().toString().length() != 0 ) {
                        // Get data values to update the test entry
                        double temp = Double.parseDouble(txtTemp.getText().toString());
                        int BPH = Integer.parseInt(txtBPH.getText().toString());
                        int BPL = Integer.parseInt(txtBPL.getText().toString());
                        double sugarLVL = Double.parseDouble(txtSugarLVL.getText().toString());
                        // Call update query
                        testViewModel.updateTest(testid, BPH, BPL, temp, sugarLVL);
                        // Indicating successful update
                        Toast.makeText(UpdateTestInfo.this, "Test #" + testid+ " was updated", Toast.LENGTH_SHORT).show();
                        // Coming back to the previous activity
                        intent = new Intent(UpdateTestInfo.this, ViewPatient.class);
                        startActivity(intent); }
                    else {
                        Toast.makeText(UpdateTestInfo.this, "Please ensure there are no null values", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Go back event handler
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UpdateTestInfo.this, ViewPatient.class);
                startActivity(intent);
            }
        });
    }
    // Validating specific inputs
    public boolean validateInfo() {
        // Get all text for further examination
        String BPHCheck = txtBPH.getText().toString();
        String BPLCheck = txtBPL.getText().toString();
        String temperatureCheck = txtTemp.getText().toString();
        String sugarLvlCheck = txtSugarLVL.getText().toString();
        // Check for only numbers input
        if(BPHCheck.matches("^[0-9\\.]+") && BPLCheck.matches("^[0-9\\.]+")
                && temperatureCheck.matches("^[0-9\\.]+") && sugarLvlCheck.matches("^[0-9\\.]+")) {
            return true; }
        else if (BPHCheck.trim().equalsIgnoreCase("") || BPLCheck.trim().equalsIgnoreCase("") ||
                temperatureCheck.trim().equalsIgnoreCase("") || sugarLvlCheck.trim().equalsIgnoreCase("")) {
            Toast.makeText(UpdateTestInfo.this, "Please ensure there are no null values", Toast.LENGTH_SHORT).show();
            return false; }
        else {
            Toast.makeText(UpdateTestInfo.this, "Please ensure there are no alphabetical values", Toast.LENGTH_SHORT).show();
            return false; }
    }
}