package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class AddPatient extends AppCompatActivity {
    // Shared Prefs
    SharedPreferences prefs;
    // ViewModels
    private PatientViewModel patientViewModel;
    // TextViews - Patient and Nurse Info
    private EditText txtNurseId;
    private EditText txtFirstName;
    private EditText txtLastName;
    private Spinner txtDepartment;
    private EditText txtRoom;
    // Buttons
    private Button btnAdd;
    private ImageButton btnBack;
    // Intent
    private Intent intent;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        // Get information from Shared Preferences
        prefs = getSharedPreferences("Nurse", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("SelectedNurse", "");
        Nurse selectedNurse = gson.fromJson(json, Nurse.class);
        // Initializing Patient text fields
        txtNurseId = (EditText)findViewById(R.id.txtAddPNurseID);
        txtNurseId.setEnabled(false);
        txtNurseId.setText(String.valueOf(selectedNurse.getNurseId()));
        txtFirstName = (EditText)findViewById(R.id.txtAddPFirstName);
        txtLastName = (EditText)findViewById(R.id.txtAddPLastName);
        txtRoom = (EditText)findViewById(R.id.txtAddPRoom);
        txtDepartment = (Spinner) findViewById(R.id.txtAddPatientDepartment);
        // Initializing Buttons
        btnAdd = (Button)findViewById(R.id.btnAddPatient);
        btnBack = (ImageButton) findViewById(R.id.btnAddPatientBack);
        // Initializing array adapters
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Departments.values());
        txtDepartment.setAdapter(adapter);
        // Initializing ViewModels
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        // Add patient event handler
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInfo()) { // Get all text for further examination
                    if (txtFirstName.getText().toString().length() != 0 && txtLastName.getText().toString().length() != 0
                            && txtRoom.getText().toString().length() != 0 ) {
                        // Get data values to add the patient entry
                        String firstName = txtFirstName.getText().toString();
                        String lastName = txtLastName.getText().toString();
                        String room = txtRoom.getText().toString();
                        Departments department = Departments.valueOf(txtDepartment.getSelectedItem().toString());
                        Patient newPatient = new Patient(firstName, lastName, department, selectedNurse.getNurseId(), room);
                        // Call insert query
                        patientViewModel.insert(newPatient);
                        // Indicating successful addition
                        Toast.makeText(AddPatient.this, "Patient was added", Toast.LENGTH_SHORT).show();
                        // Coming back to the previous activity
                        intent = new Intent(AddPatient.this, PatientActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddPatient.this, "Please ensure there are no null values", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Go back event handler
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AddPatient.this, PatientActivity.class);
                startActivity(intent);
            }
        });
    }
    // Validating specific inputs
    public boolean validateInfo() {
        // Get all text for further examination
        String firstNameCheck = txtFirstName.getText().toString();
        String lastNameCheck = txtLastName.getText().toString();
        String roomCheck = txtRoom.getText().toString();

        // If name is either empty or contains numbers - return false
        if(firstNameCheck.trim().equalsIgnoreCase("")) { txtFirstName.setError("Enter first name");  return false;}
        if(firstNameCheck.matches(".*\\d.*")) { txtFirstName.setError("No digits allowed"); return false;}

        // If name is either empty or contains numbers - return false
        if(lastNameCheck.trim().equalsIgnoreCase("")) { txtLastName.setError("Enter last name");  return false;}
        if(lastNameCheck.matches(".*\\d.*")) { txtLastName.setError("No digits allowed"); return false;}

        // If room is empty - return false
        if(roomCheck.trim().equalsIgnoreCase("")) { txtRoom.setError("Enter room");  return false;}

        return true;
    }
}