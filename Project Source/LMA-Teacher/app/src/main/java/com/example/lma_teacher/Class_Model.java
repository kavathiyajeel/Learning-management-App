package com.example.lma_teacher;

import android.os.Parcel;
import android.os.Parcelable;

public class Class_Model implements Parcelable {
    private String div;

    public Class_Model() {
    }

    public Class_Model(String div) {
        this.div = div;
    }

    protected Class_Model(Parcel in) {
        div = in.readString();
    }

    public static final Creator<Class_Model> CREATOR = new Creator<Class_Model>() {
        @Override
        public Class_Model createFromParcel(Parcel in) {
            return new Class_Model(in);
        }

        @Override
        public Class_Model[] newArray(int size) {
            return new Class_Model[size];
        }
    };

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(div);
    }
}
