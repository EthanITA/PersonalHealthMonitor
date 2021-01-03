package com.dong.personalhealthmonitor.java_classes.SQLite;

import android.content.Context;
import android.database.Cursor;

import com.dong.personalhealthmonitor.java_classes.generic.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportDB {
    public Report report;
    public InteractionSQL db;

    public ReportDB(Context context) {
        db = new InteractionSQL(context);
        report = new Report();
    }


    public void save_report() {
        report._id = db.insert(report.day, report.notes, report.height, report.weight, report.temperature, report.blood1, report.blood2);
    }

    public void delete_saved_report() {
        if (report._id != null) {
            db.delete(report._id);
            report.reset();
        }
    }

    public void update_saved_report() {
        if (report._id != null) {
            db.update(report._id, report.day, report.notes, report.height, report.weight, report.temperature, report.blood1, report.blood2);
        }

    }

    public void retrieve_report() {
        if (report._id != null) {
            Cursor cursor = db.fetch(report._id);
            if (cursor.moveToNext()) {
                report._id = cursor.getLong(cursor.getColumnIndexOrThrow(ReportsSQLHelper._ID));
                report.day = cursor.getLong(cursor.getColumnIndexOrThrow(ReportsSQLHelper.DAY));
                report.notes = cursor.getString(cursor.getColumnIndexOrThrow(ReportsSQLHelper.NOTES));
                report.height = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.HEIGHT));
                report.weight = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.WEIGHT));
                report.temperature = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.TEMP));
                report.blood1 = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.BLOOD1));
                report.blood2 = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.BLOOD2));
            }
        }
    }

    private List<Report> get_list_report(Cursor cursor) {
        List<Report> rps = new ArrayList<Report>();
        while (cursor.moveToNext()) {
            Report rp = new Report();
            rp._id = cursor.getLong(cursor.getColumnIndexOrThrow(ReportsSQLHelper._ID));
            rp.fill(
                    cursor.getLong(cursor.getColumnIndexOrThrow(ReportsSQLHelper.DAY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ReportsSQLHelper.NOTES)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.HEIGHT)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.WEIGHT)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.TEMP)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.BLOOD1)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.BLOOD2))
            );
            rps.add(rp);
        }
        return rps;
    }

    public List<Report> getReportDay(Long day) {
        return get_list_report(db.fetchDay(day));
    }

    public List<Report> getAllHeight() {

        return get_list_report(db.fetchContains(ReportsSQLHelper.HEIGHT));
    }

    public List<Report> getAllWeight() {
        return get_list_report(db.fetchContains(ReportsSQLHelper.WEIGHT));
    }

    public List<Report> getAllTemp() {
        return get_list_report(db.fetchContains(ReportsSQLHelper.TEMP));
    }

    public List<Report> getAllBlood() {
        return get_list_report(db.fetchContains(ReportsSQLHelper.BLOOD1));
    }

    public void retrieve_first() {
        Cursor cursor = db.fetchAll();
        if (cursor.moveToNext()) {
            report._id = cursor.getLong(cursor.getColumnIndexOrThrow(ReportsSQLHelper._ID));
            report.day = cursor.getLong(cursor.getColumnIndexOrThrow(ReportsSQLHelper.DAY));
            report.notes = cursor.getString(cursor.getColumnIndexOrThrow(ReportsSQLHelper.NOTES));
            report.height = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.HEIGHT));
            report.weight = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.WEIGHT));
            report.temperature = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.TEMP));
            report.blood1 = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.BLOOD1));
            report.blood2 = cursor.getDouble(cursor.getColumnIndexOrThrow(ReportsSQLHelper.BLOOD2));
        }
    }

}
