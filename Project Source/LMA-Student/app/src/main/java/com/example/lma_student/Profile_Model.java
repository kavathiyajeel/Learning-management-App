package com.example.lma_student;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile_Model implements Parcelable {
    private String fullname;
    private String username;
    private String email;
    private String gender;
    private String dob;
    private String phone;
    private String semester;
    private String division;
    private String branch;
    private String image;

    public Profile_Model() {
    }

    public Profile_Model(String fullname, String username, String email, String gender, String dob, String phone, String semester, String division, String branch, String image) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.semester = semester;
        this.division = division;
        this.branch = branch;
        this.image = image;
    }

    protected Profile_Model(Parcel in) {
        fullname = in.readString();
        username = in.readString();
        email = in.readString();
        gender = in.readString();
        dob = in.readString();
        phone = in.readString();
        semester = in.readString();
        division = in.readString();
        branch = in.readString();
        image = in.readString();
    }

    public static final Creator<Profile_Model> CREATOR = new Creator<Profile_Model>() {
        @Override
        public Profile_Model createFromParcel(Parcel in) {
            return new Profile_Model(in);
        }

        @Override
        public Profile_Model[] newArray(int size) {
            return new Profile_Model[size];
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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
        parcel.writeString(gender);
        parcel.writeString(dob);
        parcel.writeString(phone);
        parcel.writeString(semester);
        parcel.writeString(division);
        parcel.writeString(branch);
        parcel.writeString(image);
    }
}
