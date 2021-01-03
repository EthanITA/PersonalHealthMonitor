package com.dong.personalhealthmonitor.java_classes.generic;

import java.util.List;

public class Stats {
    List<Report> rp;
    CalendarManager calendar;

    public Stats(List<Report> rp) {
        this.rp = rp;
        calendar = new CalendarManager();
    }

    public String getDates() {
        long min = getMinDate();
        long max = getMaxDate();
        return min > 0L || max > 0L ? calendar.getMillisToDate(min) + " => " + calendar.getMillisToDate(max) : "";
    }

    public String getHeight() {

        double min = getMinHeight();
        double max = getMaxHeight();
        return min + " - " + max + " cm";
    }

    public String getWeight() {

        double min = getMinWeight();
        double max = getMaxWeight();
        return min + " - " + max + " Kg";
    }

    public String getTemp() {

        double min = getMinTemp();
        double max = getMaxTemp();
        return min + " - " + max + " °C";
    }

    public String getBlood() {

        double min1 = getMinBlood1();
        double max1 = getMaxBlood1();
        double min2 = getMinBlood2();
        double max2 = getMaxBlood2();

        return ("Systolic: " + min1 + " - " + max1) + "\n" + ("Diastolic: " + min2 + " - " + max2);
    }

    public long getMinDate() {
        return get((GetterLONG) (Report rp) -> rp.day, Math::min, rp.size() > 0 ? rp.get(0).day : 0L);
    }

    public long getMaxDate() {
        return get((GetterLONG) (Report rp) -> rp.day, Math::max, rp.size() > 0 ? rp.get(0).day : 0L);
    }

    public double getMinHeight() {
        return get((Report rp) -> rp.height, Math::min, rp.size() > 0 ? rp.get(0).height : 0.0);
    }

    public double getMaxHeight() {
        return get((Report rp) -> rp.height, Math::max, rp.size() > 0 ? rp.get(0).height : 0.0);
    }

    public double getMinWeight() {
        return get((Report rp) -> rp.weight, Math::min, rp.size() > 0 ? rp.get(0).weight : 0.0);
    }

    public double getMaxWeight() {
        return get((Report rp) -> rp.weight, Math::max, rp.size() > 0 ? rp.get(0).weight : 0.0);
    }

    public double getMinTemp() {
        return get((Report rp) -> rp.temperature, Math::min, rp.size() > 0 ? rp.get(0).temperature : 0.0);
    }

    public double getMaxTemp() {
        return get((Report rp) -> rp.temperature, Math::max, rp.size() > 0 ? rp.get(0).temperature : 0.0);
    }

    public double getMinBlood1() {
        return get((Report rp) -> rp.blood1, Math::min, rp.size() > 0 ? rp.get(0).blood1 : 0.0);
    }

    public double getMaxBlood1() {
        return get((Report rp) -> rp.blood1, Math::max, rp.size() > 0 ? rp.get(0).blood1 : 0.0);
    }

    public double getMinBlood2() {
        return get((Report rp) -> rp.blood2, Math::min, rp.size() > 0 ? rp.get(0).blood2 : 0.0);
    }

    public double getMaxBlood2() {
        return get((Report rp) -> rp.blood2, Math::max, rp.size() > 0 ? rp.get(0).blood2 : 0.0);
    }

    private double get(GetterDOUBLE func, MinMaxDOUBLE minmax, double first) {
        double result = first;
        for (Report r :
                rp) {
            result = minmax.get(func.get(r), result);
        }
        return result;
    }

    private long get(GetterLONG func, MinMaxLONG minmax, long first) {
        long result = first;
        for (Report r :
                rp) {
            result = minmax.get(func.get(r), result);
        }
        return result;
    }

    private interface GetterDOUBLE {
        double get(Report rp);
    }

    private interface GetterLONG {
        long get(Report rp);
    }

    private interface MinMaxDOUBLE {
        double get(double v1, double v2);
    }

    private interface MinMaxLONG {
        long get(long v1, long v2);
    }
}
