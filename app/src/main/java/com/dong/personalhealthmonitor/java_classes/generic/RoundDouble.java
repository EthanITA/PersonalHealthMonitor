package com.dong.personalhealthmonitor.java_classes.generic;

public class RoundDouble {
    int i;
    public RoundDouble(int n_dec){
        i = n_dec;
    }
    public double round(double a) {
        double b = Math.pow(10.0, i);
        return Math.round(a * b) / b;
    }
}
