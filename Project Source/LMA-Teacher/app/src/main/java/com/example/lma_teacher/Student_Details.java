package com.example.lma_teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Student_Details extends AppCompatActivity {
    private TextView fullname,username,email,gender,dob,phone,semester,division,branch;
    private Student_Model student_model;
    private ImageView sd_image;
    private Button edit;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        //SupportActionBar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Student Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.bg));



        fullname = findViewById(R.id.sd_fullname);
        username = findViewById(R.id.sd_username);
        email = findViewById(R.id.sd_email);
        gender = findViewById(R.id.sd_gender);
        dob = findViewById(R.id.sd_dob);
        phone = findViewById(R.id.sd_phone);
        semester = findViewById(R.id.sd_semester);
        division = findViewById(R.id.sd_division);
        branch = findViewById(R.id.sd_branch);
        sd_image = findViewById(R.id.sd_image);
        edit = findViewById(R.id.edit_btn);


        student_model = getIntent().getParcelableExtra("Students");
        if (student_model != null) {
            fullname.setText(student_model.getFullname());
            username.setText(student_model.getUsername());
            email.setText(student_model.getEmail());
            gender.setText(student_model.getGender());
            dob.setText(student_model.getDob());
            phone.setText(student_model.getPhone());
            semester.setText(student_model.getSemester());
            division.setText(student_model.getDivision());
            branch.setText(student_model.getBranch());
            Picasso.get().load(student_model.getImage()).into(sd_image);

        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Student_Details.this, Edit_Student.class);
                i.putExtra("Students", student_model);
                startActivity(i);
                finishAndRemoveTask();
            }
        });
    }
}