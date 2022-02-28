package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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

import java.util.List;

public class UpdatePatientInfo extends AppCompatActivity {
    // Shared Prefs
    SharedPreferences prefs;
    private PatientViewModel patientViewModel;
    // TextView Title
    private TextView lblTitle;
    // TextViews - Patient Info
    private EditText txtNurseId;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtRoom;
    private Spinner txtDepartment;
    // Buttons
    private Button btnUpdate;
    private ImageButton btnBack;
    // Intent
    private Intent intent;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient_info);
        // Get information from Shared Preferences
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        prefs = getSharedPreferences("Nurse", MODE_PRIVATE);
        int id = prefs.getInt("SelectedPatient", 0);
        lblTitle.setText("Update\nPatient #" + id + " Information");
        // Initializing Patient text fields
        txtNurseId = (EditText)findViewById(R.id.txtEditPNurseID);
        txtNurseId.setEnabled(false);
        txtFirstName = (EditText)findViewById(R.id.txtEditPFirstName);
        txtLastName = (EditText)findViewById(R.id.txtEditPLastName);
        txtRoom = (EditText)findViewById(R.id.txtEditPRoom);
        txtDepartment = (Spinner) findViewById(R.id.txtPatientDepartment);
        // Initializing Buttons
        btnUpdate = (Button)findViewById(R.id.btnUpdateSelPatient);
        btnBack = (ImageButton) findViewById(R.id.btnUpdatePatientBack);
        // Creating an array adapter for the spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Departments.values());
        txtDepartment.setAdapter(adapter);
        // Initializing ViewModels
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        // Getting patient's information
        patientViewModel.findByPatientID(id).observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(@Nullable Patient result) {
                txtFirstName.setText(result.getFirstName());
                txtLastName.setText(result.getLastName());
                txtNurseId.setText(String.valueOf(result.getNurseID()));
                String departmentName = result.getPatientDepartment().name();
                int indexDepartment = Departments.valueOf(departmentName).ordinal();
                txtDepartment.setSelection(indexDepartment);
                txtRoom.setText(result.getRoom());
            }
        });
        // Update patient event handler
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInfo()) { // Get all text for further examination
                    if (txtFirstName.getText().toString().length() != 0 && txtLastName.getText().toString().length() != 0
                            && txtRoom.getText().toString().length() != 0 ) {
                        // Get data values to update the patient entry
                        String firstName = txtFirstName.getText().toString();
                        String lastName = txtLastName.getText().toString();
                        String room = txtRoom.getText().toString();
                        Departments department = Departments.valueOf(txtDepartment.getSelectedItem().toString());
                        // Call update query
                        patientViewModel.updatePatient(id, firstName, lastName, room, department);
                        // Indicating successful update
                        Toast.makeText(UpdatePatientInfo.this, "Patient #" + id +" updated", Toast.LENGTH_SHORT).show();
                        // Coming back to the previous activity
                        intent = new Intent(UpdatePatientInfo.this, PatientActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UpdatePatientInfo.this, "Please ensure there are no null values", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // Go back event handler
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UpdatePatientInfo.this, PatientActivity.class);
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