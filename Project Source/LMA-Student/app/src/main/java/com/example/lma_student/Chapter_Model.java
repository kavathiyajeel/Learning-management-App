package com.example.lma_student;

import android.os.Parcel;
import android.os.Parcelable;

public class Chapter_Model implements Parcelable {
    private String title,url;

    public Chapter_Model() {
    }

    public Chapter_Model(String title, String url) {
        this.title = title;
        this.url = url;
    }

    protected Chapter_Model(Parcel in) {
        title = in.readString();
        url = in.readString();
    }

    public static final Creator<Chapter_Model> CREATOR = new Creator<Chapter_Model>() {
        @Override
        public Chapter_Model createFromParcel(Parcel in) {
            return new Chapter_Model(in);
        }

        @Override
        public Chapter_Model[] newArray(int size) {
            return new Chapter_Model[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(url);
    }
}
