package com.dong.personalhealthmonitor.java_classes.SQLite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class InteractionSQL {

    private ReportsSQLHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public InteractionSQL(Context c) {
        context = c;
    }

    public void open() throws SQLException {
        dbHelper = new ReportsSQLHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Long insert(Long day, String notes, double height, double weight, double temp, double blood1, double blood2) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(ReportsSQLHelper.DAY, day);
        contentValue.put(ReportsSQLHelper.NOTES, notes);
        contentValue.put(ReportsSQLHelper.HEIGHT, height);
        contentValue.put(ReportsSQLHelper.WEIGHT, weight);
        contentValue.put(ReportsSQLHelper.TEMP, temp);
        contentValue.put(ReportsSQLHelper.BLOOD1, blood1);
        contentValue.put(ReportsSQLHelper.BLOOD2, blood2);
        return database.insert(ReportsSQLHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetchContains(String attribute) {
        String selection = attribute + " != ?";
        String[] selectionArgs = {String.valueOf(0)};
        Cursor cursor = database.query(ReportsSQLHelper.TABLE_NAME, null, selection, selectionArgs, null, null, ReportsSQLHelper.DAY);

        return cursor;
    }

    public Cursor fetch(Long _id) {
        String selection = ReportsSQLHelper._ID + " = ?";
        String[] selectionArgs = {String.valueOf(_id)};
        Cursor cursor = database.query(ReportsSQLHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        return cursor;
    }

    public Cursor fetchDay(Long day) {
        String selection = ReportsSQLHelper.DAY + " = ?";
        String[] selectionArgs = {String.valueOf(day)};
        Cursor cursor = database.query(ReportsSQLHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        return cursor;
    }

    public Cursor fetchAll() {

        Cursor cursor = database.query(ReportsSQLHelper.TABLE_NAME, null, null, null, null, null, null);

        return cursor;
    }

    public int update(long _id, Long day, String notes, double height, double weight, double temp, double blood1, double blood2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReportsSQLHelper.DAY, day);
        contentValues.put(ReportsSQLHelper.NOTES, notes);
        contentValues.put(ReportsSQLHelper.HEIGHT, height);
        contentValues.put(ReportsSQLHelper.WEIGHT, weight);
        contentValues.put(ReportsSQLHelper.TEMP, temp);
        contentValues.put(ReportsSQLHelper.BLOOD1, blood1);
        contentValues.put(ReportsSQLHelper.BLOOD2, blood2);
        return database.update(ReportsSQLHelper.TABLE_NAME, contentValues, ReportsSQLHelper._ID + " = " + _id, null);
    }

    public void delete(long _id) {
        database.delete(ReportsSQLHelper.TABLE_NAME, ReportsSQLHelper._ID + "=" + _id, null);
    }

}
