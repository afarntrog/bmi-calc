package com.example.bmicalc;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // fields
    private static final String DEBUG_TAG = "MainActivityLogging";

    private BMICalc mCurrentBMICalc;

    // text fields. Note: you can only instantiate these values once the content_main is created and that happens  setContentView()
    private EditText mHeight, mWeight;
    private DecimalFormat mFormatter;

    private int mCalculationsDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();
        setUpFAB();
        setUpViews();
        // Always initialize all fields on onCreate()
        initializeFields();
        mCalculationsDone = savedInstanceState == null ? 0 : savedInstanceState.getInt("TOTAL_CALCS");

        Log.i(DEBUG_TAG, "Inside OnCreate()");
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //    Annoymous inner class
    //    Listener waits for click and on click everything will run.
    private void setUpFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                handleFABClick(view);

            }
        });
    }

    private void handleFABClick(View view) {

        String strHeight = mHeight.getText().toString();
        String strWeight = mWeight.getText().toString();

        if (strHeight.equals("") && strWeight.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Height and Weight cannot be empty",
                    Toast.LENGTH_LONG).show();
        } else {
            mCalculationsDone++;
            showBMIResults(view, strHeight, strWeight);
        }
    }

    private void showBMIResults(View view, String strHeight, String strWeight) {
        // When user clicks on the FAB then we want to get the H and W
        double height = Double.parseDouble(strHeight);
        double weight = Double.parseDouble(strWeight);


        // set the values in the BMI class/instance
        mCurrentBMICalc.setHeight(height);
        mCurrentBMICalc.setWeight(weight);

        String msg = "Clicks Total: " + mCalculationsDone + "; BMI: " + mFormatter.format(mCurrentBMICalc.getBMI());

        // Now getbmi() and show results
        Snackbar.make(view, msg,
                Snackbar.LENGTH_LONG)
                .setAction("More...", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // If user clicks More then open new Activity
                        showResultsActivity();
                    }
                }).show();
    }


    private void showResultsActivity() {
        // in order for the intent object to talk to the resultsactiviyu class we us the getApplicationContext() to be the bridge between the two
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);

        // Pass the data to resutlsActivity class
        // Option 1) Send entire mCurrentBMICalc object
        // Option 2) Send each value
        // We will use option 2, which uses a hashmap behind the putExtra method
        intent.putExtra("HEIGHT", mCurrentBMICalc.getHeight());
        intent.putExtra("WEIGHT", mCurrentBMICalc.getWeight());
        intent.putExtra("BMI", mFormatter.format(mCurrentBMICalc.getBMI()));
        intent.putExtra("BMI_GROUP", mCurrentBMICalc.getBmiGroup());

        // Now pass the intent object into the results activity. All the extra data is in the Bundle object that is part of intent.
        startActivity(intent);

    }


    private void setUpViews() {
//        this gets the  input fields. Is expensive O(n) operations. has to find ID
        mHeight = findViewById(R.id.et_height);
        mWeight = findViewById(R.id.et_weight);
    }

    private void initializeFields() {
        // Instance of Modle BMI class
        mCurrentBMICalc = new BMICalc();
        mFormatter = new DecimalFormat("00.00");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // SAVE state across rotation, store data in {key, value}
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(DEBUG_TAG, "Inside OnSaveInstance()");

        outState.putInt("TOTAL_CALCS", mCalculationsDone);
    }

/*
    // This will GET the data that is stored when calling onSaveInstanceState() on destroy, device rotation
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCalculationsDone = savedInstanceState.getInt("TOTAL_CALCS");
    }
*/

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(DEBUG_TAG, "Inside onStart()");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(DEBUG_TAG, "Inside OnRestart()");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(DEBUG_TAG, "Inside OnPause()");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(DEBUG_TAG, "Inside OnResume()");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(DEBUG_TAG, "Inside OnStop()");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(DEBUG_TAG, "Inside OnDestroy()");
    }


}
