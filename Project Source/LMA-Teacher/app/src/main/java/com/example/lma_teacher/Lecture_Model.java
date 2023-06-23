package com.example.lma_teacher;

import android.os.Parcel;
import android.os.Parcelable;

public class Lecture_Model implements Parcelable {
    private String lec_no,sub_name,sub_code,faculty,time,day;

    public Lecture_Model() {
    }

    public Lecture_Model(String lec_no, String sub_name, String sub_code, String faculty, String time, String day) {
        this.lec_no = lec_no;
        this.sub_name = sub_name;
        this.sub_code = sub_code;
        this.faculty = faculty;
        this.time = time;
        this.day = day;
    }

    protected Lecture_Model(Parcel in) {
        lec_no = in.readString();
        sub_name = in.readString();
        sub_code = in.readString();
        faculty = in.readString();
        time = in.readString();
        day = in.readString();
    }

    public static final Creator<Lecture_Model> CREATOR = new Creator<Lecture_Model>() {
        @Override
        public Lecture_Model createFromParcel(Parcel in) {
            return new Lecture_Model(in);
        }

        @Override
        public Lecture_Model[] newArray(int size) {
            return new Lecture_Model[size];
        }
    };

    public String getLec_no() {
        return lec_no;
    }

    public void setLec_no(String lec_no) {
        this.lec_no = lec_no;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lec_no);
        dest.writeString(sub_name);
        dest.writeString(sub_code);
        dest.writeString(faculty);
        dest.writeString(time);
        dest.writeString(day);
    }
}
