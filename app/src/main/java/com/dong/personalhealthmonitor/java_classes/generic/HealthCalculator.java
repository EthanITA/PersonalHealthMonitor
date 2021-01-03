package com.dong.personalhealthmonitor.java_classes.generic;

public class HealthCalculator {
    RoundDouble r;
    double height, weight;
    int age;
    boolean gender;


    public HealthCalculator(double height, double weight, int age, boolean gender) {
        r = new RoundDouble(2);
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }

    public double bmi() {
        if (height > 0)
            return r.round(weight / (height / 100 * height / 100));
        else
            return 0.0;
    }


    public String getBMI() {
        return bmi() > 0 ? bmi() + " Kg/m^2" : " - ";
    }

    public String getBMIFeedback() {
        double bmi = bmi();
        if (bmi == 0) return " - ";
        else if (bmi < 16) return "Severe Thinness";
        else if (bmi <= 17) return "Moderate Thinness";
        else if (bmi <= 18.5) return "Mild Thinness";
        else if (bmi <= 25) return "Normal";
        else if (bmi <= 30) return "Overweight";
        else return "Obese";
    }

    public double bmr() {
        // https://www.calculator.net/bmr-calculator.html
        if (weight > 0 && height > 0 && age > 0) {
            if (!gender) {
                return r.round(447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age));
            } else
                return r.round(88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age));
        } else return 0.0;
    }

    public String getBMR() {
        return bmr() > 0 ? bmr() + " Kcal/day" : " - ";
    }

    public String getBMRDescription() {
        double bmr = bmr();
        if (bmr > 0) {
            return "Sedentary: little or no exercise:" + r.round(bmr * 1.2) + "\n" +
                    "Exercise 1-3 times/week: " + r.round(bmr * 1.37) + "\n" +
                    "Exercise 4-5 times/week: " + r.round(bmr * 1.46) + "\n" +
                    "Daily exercise or intense exercise 3-4 times/week: " + r.round(bmr * 1.55) + "\n" +
                    "Intense exercise 6-7 times/week: " + r.round(bmr * 1.725) + "\n" +
                    "Very intense exercise daily, or physical job: " + r.round(bmr * 1.9);
        } else return " - ";
    }

    public double fat() {
        // https://www.calculator.net/body-fat-calculator.html BMI method
        if (age > 0 && bmi() > 0) {
            if (age <= 15) {
                return r.round(1.51 * bmi()) - (0.7 * age) + (gender ? -2.2 : 1.4);
            } else {
                return r.round((1.2 * bmi()) + (0.23 * age) - (gender ? 16.2 : 5.4));
            }
        } else {
            return 0.0;
        }
    }

    public String getFat() {
        return fat() > 0 ? fat() + " %" : " - ";
    }

    public String getFatFeedback() {
        double fat = fat();
        if (fat > 0) {
            if (gender) {
                if (fat <= 5) return "Essential fat";
                else if (fat <= 13) return "Athletes";
                else if (fat <= 17) return "Fitness";
                else if (fat <= 25) return "Average";
                else return "Obese";
            } else {
                if (fat <= 13) return "Essential fat";
                else if (fat <= 20) return "Athletes";
                else if (fat <= 24) return "Fitness";
                else if (fat <= 31) return "Average";
                else return "Obese";
            }
        } else return " - ";
    }
}