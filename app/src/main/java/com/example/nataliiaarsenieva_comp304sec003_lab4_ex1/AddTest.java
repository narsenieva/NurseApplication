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

public class AddTest extends AppCompatActivity {
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
    private Button btnAdd;
    private ImageButton btnBack;
    // Intent
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        // Get information from Shared Preferences
        prefs = getSharedPreferences("Nurse", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("SelectedNurse", "");
        Nurse selectedNurse = gson.fromJson(json, Nurse.class);
        int patientid = prefs.getInt("SelectedPatient", 0);
        // Initializing Patient and Nurse text fields
        txtNurseId = (EditText) findViewById(R.id.txtTestPNurseID);
        txtPFullName = (EditText) findViewById(R.id.txtTestPFullName);

        txtNurseId.setEnabled(false);
        txtPFullName.setEnabled(false);

        txtNurseId.setText(String.valueOf(selectedNurse.getNurseId()));
        // Initializing ViewModels
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        // Getting patient's information
        patientViewModel.findByPatientID(patientid).observe(this, new Observer<Patient>() { @Override
            public void onChanged(@Nullable Patient result) { txtPFullName.setText(result.getFirstName() + " " + result.getLastName()); }});
        // Initializing Test text fields
        txtTemp = (EditText) findViewById(R.id.txtTestPTemperature);
        txtBPH = (EditText) findViewById(R.id.txtTestPBPH);
        txtBPL = (EditText) findViewById(R.id.txtTestPBPL);
        txtSugarLVL = (EditText) findViewById(R.id.txtTestPSugarLVL);
        // Initializing Buttons
        btnAdd = (Button) findViewById(R.id.btnAddTest);
        btnBack = (ImageButton) findViewById(R.id.btnAddTestBack);
        // Add new test event handler
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInfo()) { // Get all text for further examination
                    if (txtTemp.getText().toString().length() != 0 && txtBPH.getText().toString().length() != 0
                            && txtBPL.getText().toString().length() != 0 && txtSugarLVL.getText().toString().length() != 0 ) {
                        // Get data values to add the test entry
                        double temp = Double.parseDouble(txtTemp.getText().toString());
                        int BPH = Integer.parseInt(txtBPH.getText().toString());
                        int BPL = Integer.parseInt(txtBPL.getText().toString());
                        double sugarLVL = Double.parseDouble(txtSugarLVL.getText().toString());
                        Test newTest = new Test(BPL, BPH, temp, sugarLVL, patientid, selectedNurse.getNurseId());
                        // Call insert query
                        testViewModel.insert(newTest);
                        // Indicating successful addition
                        Toast.makeText(AddTest.this, "New Test Added", Toast.LENGTH_SHORT).show();
                        // Coming back to the previous activity
                        intent = new Intent(AddTest.this, ViewPatient.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddTest.this, "Please ensure there are no null values", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // Go back event handler
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AddTest.this, ViewPatient.class);
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
            Toast.makeText(AddTest.this, "Please ensure there are no null values", Toast.LENGTH_SHORT).show();
            return false; }
        else {
            Toast.makeText(AddTest.this, "Please ensure there are no alphabetical values", Toast.LENGTH_SHORT).show();
            return false; }
    }
}