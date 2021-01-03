package com.dong.personalhealthmonitor.java_classes.generic.entries;

import com.dong.personalhealthmonitor.java_classes.generic.Report;
import com.dong.personalhealthmonitor.java_classes.generic.RoundDouble;

import java.util.List;

public class AverageOfReports {
    List<Report> reports;
    Height height;
    Weight weight;
    Temperature temperature;
    Blood blood;
    RoundDouble r1, r2;

    public AverageOfReports(List<Report> rps) {
        reports = rps;
        height = new Height();
        weight = new Weight();
        temperature = new Temperature();
        blood = new Blood();
        r1 = new RoundDouble(1);
        r2 = new RoundDouble(2);
        for (Report rp :
                reports) {
            if (rp.height > 0) {
                height.val += rp.height;
                height.size += 1;
            }

            if (rp.weight > 0) {
                weight.val += rp.weight;
                weight.size += 1;
            }
            if (rp.temperature > 0) {
                temperature.val += rp.temperature;
                temperature.size += 1;
            }
            if (rp.blood1 > 0 && rp.blood2 > 0) {
                blood.val1 += rp.blood1;
                blood.val2 += rp.blood2;
                blood.size += 1;
            }
        }
    }

    public int getSize() {
        return reports.size();
    }

    public Report getReport() {
        Report result = new Report();
        if (getSize() == 0) {
            return result;
        } else {
            result.fill(reports.get(0).day, "", getAverageHeight(), getAverageWeight(), getAverageTemperature(), getAverageBlood1(), getAverageBlood2());
        }
        return result;
    }

    private double getAverage(double sum, int size, RoundDouble r) {
        return size > 0 ? r.round(sum / size) : 0.0;
    }

    public double getAverageHeight() {
        return getAverage(height.val, height.size, r2);
    }

    public double getAverageWeight() {
        return getAverage(weight.val, weight.size, r2);

    }

    public double getAverageTemperature() {
        return getAverage(temperature.val, temperature.size, r1);

    }

    public double getAverageBlood1() {
        return getAverage(blood.val1, blood.size, r1);

    }

    public double getAverageBlood2() {
        return getAverage(blood.val2, blood.size, r1);

    }

    public int getHeightSize() {
        return height.size;
    }

    public int getWeightSize() {
        return weight.size;
    }

    public int getTemperatureSize() {
        return temperature.size;
    }

    public int getBloodSize() {
        return blood.size;
    }

}
