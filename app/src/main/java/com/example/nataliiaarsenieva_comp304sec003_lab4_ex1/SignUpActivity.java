package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    // ViewModels
    private NurseViewModel nurseViewModel;
    // TextViews - Nurse Info
    private EditText txtNurseId;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtPassword;
    private Spinner txtDepartment;
    // Buttons
    private ImageButton btnBack;
    // Intent
    private Intent intent;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Initializing ViewModels
        nurseViewModel = new ViewModelProvider(this).get(NurseViewModel.class);
        txtDepartment = (Spinner)findViewById(R.id.txtDepartment);
        // Initializing Buttons
        btnBack = (ImageButton) findViewById(R.id.btnSignUpBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Departments.values());
        txtDepartment.setAdapter(adapter);
    }

    // Sign Up event handler
    public void signUpNurse (View view) {
        // Initializing Nurse text fields
        txtNurseId = (EditText)findViewById(R.id.txtEditPFirstName);
        txtFirstName = (EditText)findViewById(R.id.txtSignInPassword);
        txtLastName = (EditText)findViewById(R.id.txtLastName);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        if(validateInfo()) { // Get all text for further examination
            if (txtNurseId.getText().toString().length() != 0 && txtFirstName.getText().toString().length() != 0
                    && txtLastName.getText().toString().length() != 0 &&
                    txtPassword.getText().toString().length() != 0) {
                // Get data values to update the nurse entry
                String firstNameValue = txtFirstName.getText().toString();
                String lastNameValue = txtLastName.getText().toString();
                String passwordValue = txtPassword.getText().toString();
                Departments departmentsValue = (Departments) txtDepartment.getSelectedItem();
                int nurseIDValue = Integer.parseInt(txtNurseId.getText().toString());
                // Call insert query
                nurseViewModel.insert(new Nurse(nurseIDValue, firstNameValue, lastNameValue, passwordValue, departmentsValue));
                //finish();
                // Indicating successful update
                Toast.makeText(SignUpActivity.this, "Thanks for signing up, " + firstNameValue +"!", Toast.LENGTH_SHORT).show();
                // Coming back to the previous activity
                intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(SignUpActivity.this, "Please ensure there are no null values", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Validating specific inputs
    public boolean validateInfo() {
        // Get all text for further examination
        String firstNameCheck = txtFirstName.getText().toString();
        String lastNameCheck = txtLastName.getText().toString();
        String idCheck = txtNurseId.getText().toString();
        String passwordCheck = txtPassword.getText().toString();
        boolean idMatch = false;

        // If id contains letters = return false
        if (idCheck.matches("^[0-9]+")) { idMatch = true; } else { idMatch = false; }
        if(idCheck.trim().equalsIgnoreCase("")) { txtNurseId.setError("Enter ID");  return false;}
        if(!idMatch) { txtNurseId.setError("No letters allowed"); return false; }

        // If name is either empty or contains numbers - return false
        if(firstNameCheck.trim().equalsIgnoreCase("")) { txtFirstName.setError("Enter first name");  return false;}
        if(firstNameCheck.matches(".*\\d.*")) { txtFirstName.setError("No digits allowed"); return false;}

        // If name is either empty or contains numbers - return false
        if(lastNameCheck.trim().equalsIgnoreCase("")) { txtLastName.setError("Enter last name");  return false;}
        if(lastNameCheck.matches(".*\\d.*")) { txtLastName.setError("No digits allowed"); return false;}

        if(passwordCheck.trim().equalsIgnoreCase("")) { txtPassword.setError("Enter password");  return false;}

        return true;
    }
}
