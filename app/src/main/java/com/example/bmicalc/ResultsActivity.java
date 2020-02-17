package com.example.bmicalc;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    // fields to accepts passed in data
    private TextView mHeight, mWeight, mBMI, mBMI_Group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setupToolbar();
        setupFAB();
        setupViews();
        processIncomingData();

    }


    private void processIncomingData() {
        // create bundle object that refers to the bundle inside the intent
        Bundle extras = getIntent().getExtras();

        // Get the values from the bundle. {key, value}
        double height = extras.getDouble("HEIGHT");
        double weight = extras.getDouble("WEIGHT");
        String BMI = extras.getString("BMI");
        String BMI_Group = extras.getString("BMI_GROUP");

        // Build a string to be displayed to each text view
        String strHeight = getString(R.string.height_inches) + ": " + height;
        String strWeight = getString(R.string.weight_pounds) + ": " + weight;
        String strBMI = getString(R.string.BMI) + ": " + BMI;
        String strBMI_Group = getString(R.string.BMI_Group) + ": " + BMI_Group;

        // Set the string to each TextView
        mHeight.setText(strHeight);
        mWeight.setText(strWeight);
        mBMI.setText(strBMI);
        mBMI_Group.setText(strBMI_Group);
    }


    private void setupViews() {
        // This will give us ACCESS to each of these TextViews
        mHeight = findViewById(R.id.tv_height);
        mWeight = findViewById(R.id.tv_weight);
        mBMI = findViewById(R.id.tv_BMI);
        mBMI_Group = findViewById(R.id.tv_BMI_Group);
    }


    private void setupFAB() {
        // this is our FAB - return button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this will exit this activity, returning user to the home screen
                finish();
            }
        });
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /* This will allow us to prevent the Back Button from resetting the form data.
     * Checks if the item/button that was clicked is the back button.
     * If it is then it will call finish() to save the form data and prefill it.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}
