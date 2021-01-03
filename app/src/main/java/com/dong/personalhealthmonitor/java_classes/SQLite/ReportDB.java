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


    private List<Report> getListReports(Cursor cursor) {
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

    public List<Report> getReportsDay(Long day) {
        return getListReports(db.fetchDay(day));
    }

    public List<Report> getAllHeight() {

        return getListReports(db.fetchContains(ReportsSQLHelper.HEIGHT));
    }

    public List<Report> getAllWeight() {
        return getListReports(db.fetchContains(ReportsSQLHelper.WEIGHT));
    }

    public List<Report> getAllTemp() {
        return getListReports(db.fetchContains(ReportsSQLHelper.TEMP));
    }

    public List<Report> getAllBlood() {
        return getListReports(db.fetchContains(ReportsSQLHelper.BLOOD1));
    }


}
