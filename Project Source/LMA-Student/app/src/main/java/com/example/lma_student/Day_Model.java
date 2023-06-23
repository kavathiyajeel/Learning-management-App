package com.example.lma_student;

import android.os.Parcel;
import android.os.Parcelable;

public class Day_Model implements Parcelable {
    private String day;

    public Day_Model() {
    }

    public Day_Model(String day) {
        this.day = day;
    }

    protected Day_Model(Parcel in) {
        day = in.readString();
    }

    public static final Creator<Day_Model> CREATOR = new Creator<Day_Model>() {
        @Override
        public Day_Model createFromParcel(Parcel in) {
            return new Day_Model(in);
        }

        @Override
        public Day_Model[] newArray(int size) {
            return new Day_Model[size];
        }
    };

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(day);
    }
}
