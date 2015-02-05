package com.example.android.pathlossestimator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class MeasurementDatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "measurements";
    private static final String TABLE_BSSIDS = "bssids";
    private static final String TABLE_MEASUREMENTS = "measurements";
    private static final String TABLE_COEFFICIENTS = "coefficients";

    // bssids table columns names
    private static final String KEY_BSSID_ID = "id";
    private static final String KEY_BSSID_NAME = "name";

    // measurements table column names
    private static final String KEY_MEASUREMENT_ID = "id";
    private static final String KEY_BSSID = "id_bssid";
    private static final String KEY_RSS = "value_rss";
    private static final String KEY_DISTANCE = "value_distance";

    // coefficients table column names
    private static final String KEY_COEFFICIENT_ID = "id";
    //private static final String KEY_BSSID = "id_bssid";
    private static final String KEY_COEFFICIENT_VALUE = "value_coefficient";



    // AP MAC ADDRESS LIST
    private static final String BSSID1 = "00:17:0f:d9:71:d";
    private static final String BSSID2 = "00:1b:0a:d9:71:d"; // Wrong MAC
    private static final String BSSID3 = "00:17:0f:d9:6f:d";
    private static final String BSSID4 = "f4:7f:35:f6:ab:a";
    private static final String BSSID5 = "18:33:9d:fe:9c:6";
    private static final String BSSID6 = "18:33:9d:fe:91:c";
    private static final String BSSID7 = "18:33:9d:f9:31:8";
    private static final String BSSID8 = "18:33:9d:f9:84:7";
    private static final String BSSID9 = "04:da:d2:a7:2f:c";
    private static final String BSSID10 = "04:da:d2:29:bf:8";
    private static final String BSSID11 = "04:da:d2:57:0a:3";
    private static final String BSSID12 = "04:da:d2:29:c4:c";
    private static final String BSSID13 = "04:da:d2:57:0a:3";
    private static final String BSSID14 = "04:da:d2:56:ee:e";
    private static final String BSSID15 = "04:da:d2:29:c2:3";
    private static final String BSSID16 = "04:da:d2:57:0d:a";
    private static final String BSSID17 = "04:da:d2:29:b4:0";
    private static final String BSSID18 = "04:da:d2:57:0e:5";


    /**
     * CONSTRUCTORS
     */
    public MeasurementDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * INSTANCE METHODS
     */

    /** Only runs once, when there is no database in the system */

    public void onCreate(SQLiteDatabase db) {

        // create bssids table
        String CREATE_BSSID_TABLE = "CREATE TABLE " + TABLE_BSSIDS + "("
                + KEY_BSSID_ID + " INTEGER PRIMARY KEY,"
                + KEY_BSSID_NAME + " TEXT" + ")";
        db.execSQL(CREATE_BSSID_TABLE);

        // fills bssids table
        String [] bssids = {
                BSSID1, BSSID2, BSSID3, BSSID4, BSSID5, BSSID6, BSSID7, BSSID8, BSSID9, BSSID10,
                BSSID11, BSSID12, BSSID13, BSSID14, BSSID15, BSSID16, BSSID17, BSSID18

        };
        ContentValues bssidValues = new ContentValues();
        for (int i = 1; i < bssids.length + 1; i++) {
            bssidValues.put(KEY_BSSID_NAME, bssids[i-1]);
            // insert bssid names into bssids table
            db.insert(TABLE_BSSIDS, null, bssidValues);
        }

        // create measurements table
        String CREATE_MEASUREMENTS_TABLE = "CREATE TABLE " + TABLE_MEASUREMENTS + "("
                + KEY_MEASUREMENT_ID + " INTEGER PRIMARY KEY,"
                + KEY_BSSID + " INTEGER,"
                + KEY_RSS + " INTEGER,"
                + KEY_DISTANCE + " INTEGER" + ")";
        db.execSQL(CREATE_MEASUREMENTS_TABLE);

        // create coefficients table
        String CREATE_COEFFICIENTS_TABLE = "CREATE TABLE " + TABLE_COEFFICIENTS + "("
                + KEY_COEFFICIENT_ID + " INTEGER PRIMARY KEY,"
                + KEY_BSSID + " INTEGER,"
                + KEY_COEFFICIENT_VALUE + " DOUBLE" + ")";
        db.execSQL(CREATE_COEFFICIENTS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BSSIDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASUREMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COEFFICIENTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * getBssidNameDB (int id_BSSID)
     * Gets MAC/BSSID address which corresponds to id_BSSID
     *
     * @param id_BSSID Identification number of currently used AP MAC/BSSID
     */
    public String getBssidNameDB (int id_BSSID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BSSIDS, new String[]{KEY_BSSID_NAME}, KEY_BSSID_ID + "=?",
                new String[]{String.valueOf(id_BSSID)}, null, null, null, null);

        String bssid = "";
        if (cursor.moveToFirst()) {
            bssid = cursor.getString(0);
        }
        db.close();
        return bssid;

    }

    /**
     * addMeasurementDB (int id_BSSID, int value_RSS, int value_distance)
     *
     * Adds measurement sets to "measurements" table
     *
     * @param id_BSSID Identification number of currently used AP MAC/BSSID
     * @param value_RSS RSS value gathered from AP for a determined value_distance
     * @param value_distance meters away from where value_RSS was measured
     */

    public void addMeasurementDB (int id_BSSID, int value_RSS, int value_distance) {

        // If id_BSSID is already in table, update measurements

        // If id_BSSID is not in table, insert measurements

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues measurementValues = new ContentValues();
        measurementValues.put(KEY_BSSID, id_BSSID);
        measurementValues.put(KEY_RSS, value_RSS);
        measurementValues.put(KEY_DISTANCE, value_distance);

        // insert AP measurements into measurements table
        db.insert(TABLE_MEASUREMENTS, null, measurementValues);
        db.close();
    }

    /**
     * Reads the RSS array which corresponds to id_BSSID from measurements table
     * @param id_BSSID Identification number of currently used AP MAC/BSSID
     * @return rssArray
     */


    public double [] getRSSValuesDB (int id_BSSID){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEASUREMENTS, new String[]{KEY_RSS},
                KEY_BSSID + "=?",
                new String[]{String.valueOf(id_BSSID)}, null, null, null, null);

        double [] rssArray = new double[cursor.getCount()];
        // There is at least one register
        if (cursor.moveToFirst()) {
            for (int i = 0; i<cursor.getCount(); i++){
                rssArray[i] = cursor.getDouble(0);
                cursor.moveToNext();
            }
        }
        db.close();
        return rssArray;
    }

    /**
     * Reads the distance array which corresponds to id_BSSID from measurements table
     * @param id_BSSID Identification number of currently used AP MAC/BSSID
     * @return distanceArray
     */

    public double [] getDistanceValuesDB (int id_BSSID){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEASUREMENTS, new String[]{KEY_DISTANCE},
                KEY_BSSID + "=?",
                new String[]{String.valueOf(id_BSSID)}, null, null, null, null);

        double [] distanceArray = new double[cursor.getCount()];
        // There is at least one register
        if (cursor.moveToFirst()) {
            for (int i = 0; i<cursor.getCount(); i++){
                distanceArray[i] = cursor.getDouble(0);
                cursor.moveToNext();
            }
        }
        db.close();
        return distanceArray;
    }


    public void addCoefficientsDB (int id_BSSID, double [] coefficients) {

        // If id_BSSID is already in table, update coefficients

        // If id_BSSID is not in table, insert coefficients

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues coefficientValues = new ContentValues();

        coefficientValues.put(KEY_BSSID, id_BSSID);
        coefficientValues.put(KEY_COEFFICIENT_VALUE, coefficients[0]);
        db.insert(TABLE_COEFFICIENTS, null, coefficientValues);

        coefficientValues.put(KEY_BSSID, id_BSSID);
        coefficientValues.put(KEY_COEFFICIENT_VALUE, coefficients[1]);
        db.insert(TABLE_COEFFICIENTS, null, coefficientValues);


        coefficientValues.put(KEY_BSSID, id_BSSID);
        coefficientValues.put(KEY_COEFFICIENT_VALUE, coefficients[2]);
        db.insert(TABLE_COEFFICIENTS, null, coefficientValues);


        coefficientValues.put(KEY_BSSID, id_BSSID);
        coefficientValues.put(KEY_COEFFICIENT_VALUE, coefficients[3]);
        db.insert(TABLE_COEFFICIENTS, null, coefficientValues);

        db.close();
    }





    /** USED TO SEE DATABASE */
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"mesage"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }


    }
}