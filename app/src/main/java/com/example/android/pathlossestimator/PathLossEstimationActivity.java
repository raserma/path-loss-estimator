package com.example.android.pathlossestimator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class PathLossEstimationActivity extends Activity {
    private int mIdBssidApSelected;
    private double [] mRssValuesDB;
    private double [] mDistanceValuesDB;

    MeasurementDatabaseHandler measdbh;

    public int getmIdBssidApSelected() {
        return mIdBssidApSelected;
    }

    public void setmIdBssidApSelected(int mIdBssidApSelected) {
        this.mIdBssidApSelected = mIdBssidApSelected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_loss_estimation);
        initiateAndroid();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_path_loss_estimation, menu);
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

    private void initiateAndroid() {
        // Creation of MAC/BSSID database
        measdbh = new MeasurementDatabaseHandler(this);

        // Dialog for introducing desired AP MAC address
        dialogBssid();
    }

    /** Asks for the selected AP from where user will estimate PathLoss */
    private void dialogBssid() {

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a number
        input.setInputType(InputType.TYPE_CLASS_NUMBER);


        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_bssid)
                .setView(input)
                        // Set up the buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setmIdBssidApSelected(Integer.parseInt(input.getText().toString()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        // code = 0 -> error
                        setmIdBssidApSelected(0);
                    }
                })
                .show();
    }

    public void startPolynomialRegression(View view){

        // We get set of data
        mRssValuesDB = measdbh.getRSSValuesDB (getmIdBssidApSelected());
        mDistanceValuesDB = measdbh.getDistanceValuesDB(getmIdBssidApSelected());

        // Curve fitting using 4th order polynomial regression

        // Store it on database


    }

}
