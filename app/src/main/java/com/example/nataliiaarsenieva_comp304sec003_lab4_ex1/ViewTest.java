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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

public class ViewTest extends AppCompatActivity {
    // Shared Prefs
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    // ViewModels
    private PatientViewModel patientViewModel;
    private TestViewModel testViewModel;
    // TextViews - Test Info
    private TextView vTTitle;
    private TextView vTPatientID;
    private TextView vTFullName;
    private TextView vTNurseName;
    private TextView vTTemperature;
    private TextView vTBPH;
    private TextView vTBPL;
    private TextView vTSugarLVL;
    private ImageButton btnBack;
    // Intent
    private Intent intent;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        // Get information from Shared Preferences
        prefs = getSharedPreferences("Nurse", MODE_PRIVATE);
        prefs = getSharedPreferences("Nurse", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("SelectedNurse", "");
        Nurse selectedNurse = gson.fromJson(json, Nurse.class);
        int patientID = prefs.getInt("SelectedPatient", 0);
        int testID = prefs.getInt("SelectedTest", 0);
        // Initializing Test text fields
        vTTitle = (TextView) findViewById(R.id.txtInfoTest);
        vTPatientID = (TextView) findViewById(R.id.txtViewTPatientID);
        vTFullName = (TextView) findViewById(R.id.txtViewPFullName);
        vTNurseName = (TextView) findViewById(R.id.txtViewTPNurse);
        vTTemperature = (TextView) findViewById(R.id.txtViewPTemperature);
        vTBPH = (TextView) findViewById(R.id.txtViewPBPH);
        vTBPL = (TextView) findViewById(R.id.txtViewPBPL);
        vTSugarLVL = (TextView) findViewById(R.id.txtViewPSugarLVL);
        // Initializing buttons
        btnBack = (ImageButton) findViewById(R.id.btnViewTestBack);
        // Initializing ViewModels
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);

        // Filling up information about test id and assigned nurse
        vTTitle.setText("Test #" + testID + " Information");
        vTNurseName.setText(selectedNurse.getNFirstName() + " " + selectedNurse.getNLastName());

        // Getting patient's information
        patientViewModel.findByPatientID(patientID).observe(this, new Observer<Patient>() { @Override
        public void onChanged(@Nullable Patient result) {
            vTFullName.setText(result.getFirstName() + " " + result.getLastName());
            vTPatientID.setText(String.valueOf(result.getPatientID()));
        }});

        // Getting test's information
        testViewModel.getTestByID(testID).observe(this, new Observer<Test>() {
            @Override
            public void onChanged(@Nullable Test result) {
                vTTemperature.setText(String.valueOf(result.getTemperature()));
                vTBPH.setText(String.valueOf(result.getBPH()));
                vTBPL.setText(String.valueOf(result.getBPL()));
                vTSugarLVL.setText(String.valueOf(result.getSugarLvl()));
            }
        });

        // Go back event handler
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ViewTest.this, ViewPatient.class);
                startActivity(intent);
            }
        });
    }
}