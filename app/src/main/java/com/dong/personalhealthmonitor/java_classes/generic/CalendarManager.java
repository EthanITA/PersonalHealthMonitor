package com.dong.personalhealthmonitor.java_classes.generic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.material.datepicker.CalendarConstraints;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class CalendarManager {
    public Long getNowMillis() {

        Calendar calendarEnd = Calendar.getInstance();
        LocalDate currentdate = LocalDate.now();
        int endYear = currentdate.getYear();
        int endMonth = currentdate.getMonthValue();
        int endDate = currentdate.getDayOfMonth();
        calendarEnd.set(endYear, endMonth - 1, endDate);
        return calendarEnd.getTimeInMillis();
    }

    public Long getTodayMillis() {
        Long one_day = 86400000L;
        return (int) (getNowMillis() / one_day) * one_day;
    }

    public String getMillisToDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public CalendarConstraints.Builder limitFutureDates() {
        class RangeValidator implements CalendarConstraints.DateValidator {

            public final Parcelable.Creator<RangeValidator> CREATOR = new Parcelable.Creator<RangeValidator>() {

                @Override
                public RangeValidator createFromParcel(Parcel parcel) {
                    return new RangeValidator(parcel);
                }

                @Override
                public RangeValidator[] newArray(int size) {
                    return new RangeValidator[size];
                }
            };
            long minDate, maxDate;

            RangeValidator(long minDate, long maxDate) {
                this.minDate = minDate;
                this.maxDate = maxDate;
            }

            RangeValidator(Parcel parcel) {
                minDate = parcel.readLong();
                maxDate = parcel.readLong();
            }

            @Override
            public boolean isValid(long date) {
                return !(minDate > date || maxDate < date);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(minDate);
                dest.writeLong(maxDate);
            }

        }
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();

        Calendar calendarStart = Calendar.getInstance();

        int startYear = 2010;
        int startMonth = 1;
        int startDate = 1;


        calendarStart.set(startYear, startMonth - 1, startDate - 1);

        long minDate = calendarStart.getTimeInMillis();
        long maxDate = getNowMillis();


        constraintsBuilderRange.setStart(minDate);
        constraintsBuilderRange.setEnd(maxDate);
        constraintsBuilderRange.setValidator(new RangeValidator(minDate, maxDate));

        return constraintsBuilderRange;
    }

}
