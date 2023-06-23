package com.example.lma_teacher;

import android.os.Parcel;
import android.os.Parcelable;

public class User_Model implements Parcelable {
    private   String fullname,username,email, pass,gender,dob,cno,graduation,image;

    public User_Model() {
    }

    public User_Model(String fullname, String username, String email, String pass, String gender, String dob, String cno, String graduation, String image) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.pass = pass;
        this.gender = gender;
        this.dob = dob;
        this.cno = cno;
        this.graduation = graduation;
        this.image = image;
    }

    protected User_Model(Parcel in) {
        fullname = in.readString();
        username = in.readString();
        email = in.readString();
        pass = in.readString();
        gender = in.readString();
        dob = in.readString();
        cno = in.readString();
        graduation = in.readString();
        image = in.readString();
    }

    public static final Creator<User_Model> CREATOR = new Creator<User_Model>() {
        @Override
        public User_Model createFromParcel(Parcel in) {
            return new User_Model(in);
        }

        @Override
        public User_Model[] newArray(int size) {
            return new User_Model[size];
        }
    };

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullname);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(pass);
        parcel.writeString(gender);
        parcel.writeString(dob);
        parcel.writeString(cno);
        parcel.writeString(graduation);
        parcel.writeString(image);
    }
}
