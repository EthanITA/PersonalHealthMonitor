package com.dong.personalhealthmonitor.java_classes.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReportsSQLHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "REPORTS";

    // Table columns
    public static final String _ID = "_id";
    public static final String DAY = "day";
    public static final String NOTES = "notes";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String TEMP = "temperature";
    public static final String BLOOD1 = "blood1";
    public static final String BLOOD2 = "blood2";


    // Database Information
    static final String DB_NAME = "REPORTS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "" + DAY + " DECIMAL(10,0) NOT NULL, " +
            NOTES + " TEXT, " +
            HEIGHT + " DECIMAL(5, 2), " +
            WEIGHT + " DECIMAL(5, 2), " +
            TEMP + " DECIMAL(3, 1), " +
            BLOOD1 + " DECIMAL(5,2), " +
            BLOOD2 + " DECIMAL(5,2) " +
            ");";

    public ReportsSQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
