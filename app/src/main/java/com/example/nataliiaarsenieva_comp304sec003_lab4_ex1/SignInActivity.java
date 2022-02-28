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

import java.util.List;

public class SignInActivity extends AppCompatActivity {
    // Shared Prefs
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    // ViewModels
    private NurseViewModel nurseViewModel;
    // Buttons
    private Button btnSignIn;
    private Button btnSignUp;
    private ImageButton btnBack;
    // TextViews - Nurse Info
    private EditText txtNurseID;
    private EditText txtPassword;
    private String output;
    // Intent
    private Intent intent;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // Initializing Buttons
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnUpdateSelPatient);
        btnBack = (ImageButton) findViewById(R.id.btnSignInBack);
        // Initializing ViewModels
        nurseViewModel = new ViewModelProvider(this).get(NurseViewModel.class);
        // Get information from Shared Preferences
        prefs = getSharedPreferences("Nurse", 0);
        prefsEditor = prefs.edit();

        nurseViewModel.getAllNurses().observe(this, new Observer<List<Nurse>>() {
            @Override
            public void onChanged(@Nullable List<Nurse> result) {
                output = "";
                for(Nurse nurse : result) {
                    output+= nurse.getNFirstName() +"\n";
                }
                //textViewDisplay.setText(output);
            }
        });
        // Go back event handler
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Sign In event handler
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            //Implement the event handler method
            public void onClick(View v) {
                txtNurseID = findViewById(R.id.txtEditPFirstName);
                txtPassword = findViewById(R.id.txtSignInPassword);
                String password = txtPassword.getText().toString();
                if(txtNurseID.getText().toString().length() != 0 && txtPassword.getText().toString().length() != 0) {
                    int nurseId = Integer.parseInt(txtNurseID.getText().toString());
                    if (nurseViewModel.checkIfNurseExists(nurseId)) {
                        if(nurseViewModel.validatePassword(nurseId).equals(password)) {
                            Toast.makeText(SignInActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                            // Pass nurse obj to shared prefs
                            Nurse selectedNurse = nurseViewModel.getNurse(nurseId);
                            Gson gson = new Gson();
                            String json = gson.toJson(selectedNurse);
                            prefsEditor.putString("SelectedNurse", json);
                            prefsEditor.commit();
                            // Start activity
                            intent = new Intent(SignInActivity.this, PatientActivity.class);
                            startActivity(intent); }
                        else { Toast.makeText(SignInActivity.this, "Passwords don't match!", Toast.LENGTH_SHORT).show(); } }
                    else { Toast.makeText(SignInActivity.this, "NurseID doesn't exist!", Toast.LENGTH_SHORT).show(); } }
                else { Toast.makeText(SignInActivity.this, "Please enter your credentials", Toast.LENGTH_SHORT).show(); }
            }
        });

        // Sign Up event handler
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }

        });
    }
    //
    public void getList(View view)
    {
        nurseViewModel.getAllNurses();
    }
}
