package com.dong.personalhealthmonitor.java_classes.generic;

import androidx.annotation.NonNull;

public class Report {

    public Long day, _id;
    public String notes;
    public Double height, weight, temperature, blood1, blood2;

    public Report() {
        init();
    }

    private void init() {
        day = 0L;
        _id = 0L;
        notes = "";
        height = 0.0;
        weight = 0.0;
        temperature = 0.0;
        blood1 = 0.0;
        blood2 = 0.0;
    }

    public void fill(Long day, String notes, Double height, Double weight, Double temperature, Double blood1, Double blood2) {
        this.day = day;
        this.notes = notes;
        this.height = height;
        this.weight = weight;
        this.temperature = temperature;
        this.blood1 = blood1;
        this.blood2 = blood2;
    }

    public String getHeight() {
        return height + " cm";
    }

    public String getWeight() {
        return weight + " Kg";
    }

    public String getTemp() {
        return temperature + " °C";
    }

    public String getBlood() {
        return blood1 + "/" + blood2 + " mmHg";
    }

    public String getID() {
        return "No." + _id;
    }

    public String getNotes() {
        return notes;
    }

    public void reset() {
        init();
    }

    @NonNull
    @Override
    public String toString() {
        return "id: " + _id + "\t"
                + "day: " + day + "\t"
                + "notes: " + notes + "\t"
                + "height: " + height + "\t"
                + "weight: " + weight + "\t"
                + "temp: " + temperature + "\t"
                + "blood: " + blood1 + "/" + blood2;
    }
}
