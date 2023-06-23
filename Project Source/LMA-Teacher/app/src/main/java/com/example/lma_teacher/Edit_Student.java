package com.example.lma_teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Objects;

public class Edit_Student extends AppCompatActivity {
    private AutoCompleteTextView branch_txt;
    private RadioButton male_es, female_es;
    private TextInputLayout field_fullname, field_eno, field_email, field_password, field_cpassword, field_dob, field_phone, field_semester, field_division, field_branch;
    private TextInputEditText fullname_txt, username_txt, email_txt, password_txt, cpassword_txt, dob_txt, phone_txt, semester_txt, division_txt;
    private Button update;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Student_Model student_model;
    private ProgressBar loadingbar;
    ImageView profile_es;
    private StorageReference storageReference;
    private Uri uri;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Student");
        loadingbar = findViewById(R.id.es_loading);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.bg));


        firebaseDatabase = FirebaseDatabase.getInstance();
        fullname_txt = findViewById(R.id.es_fullname);
        username_txt = findViewById(R.id.es_username);
        email_txt = findViewById(R.id.es_email);
        password_txt = findViewById(R.id.es_password);
        cpassword_txt = findViewById(R.id.es_cpassword);
        phone_txt = findViewById(R.id.es_phone);
        dob_txt = findViewById(R.id.es_dob);
        semester_txt = findViewById(R.id.es_semester);
        division_txt = findViewById(R.id.es_division);
        branch_txt = findViewById(R.id.es_branch);
        profile_es = findViewById(R.id.es_profile);
        male_es = findViewById(R.id.es_male);
        female_es = findViewById(R.id.es_female);
        update = findViewById(R.id.es_update_btn);
        profile_es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dob_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Edit_Student.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                month = month + 1;
                String date = dayofMonth + "/" + month + "/" + year;
                dob_txt.setText(date);

            }
        };
        profile_es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        student_model = getIntent().getParcelableExtra("Students");
        if (student_model != null) {
            fullname_txt.setText(student_model.getFullname());
            username_txt.setText(student_model.getUsername());
            email_txt.setText(student_model.getEmail());
            password_txt.setText(student_model.getPassword());
            cpassword_txt.setText(student_model.getPassword());

            String gender = student_model.getGender();
            if (gender.equals("Male")) {
                male_es.setChecked(true);
            } else {
                female_es.setChecked(true);
            }
            dob_txt.setText(student_model.getDob());
            phone_txt.setText(student_model.getPhone());
            semester_txt.setText(student_model.getSemester());
            division_txt.setText(student_model.getDivision());
            Picasso.get().load(student_model.getImage()).into(profile_es);
        }

        String userid = student_model.getUserid();
        databaseReference = firebaseDatabase.getReference("Students").child(userid);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateDate();
            }

            private void ValidateDate() {

                field_fullname = findViewById(R.id.es_fullname_field);
                field_eno = findViewById(R.id.es_username_field);
                field_email = findViewById(R.id.es_email_field);
                field_password = findViewById(R.id.es_password_field);
                field_cpassword = findViewById(R.id.es_cpassword_field);
                field_dob = findViewById(R.id.es_dob_field);
                field_phone = findViewById(R.id.es_phone_field);
                field_semester = findViewById(R.id.es_semester_field);
                field_division = findViewById(R.id.es_division_field);
                field_branch = findViewById(R.id.es_branch_field);


                String fullname = field_fullname.getEditText().getText().toString();
                String username = field_eno.getEditText().getText().toString();
                String email = field_email.getEditText().getText().toString();
                String password = field_password.getEditText().getText().toString();
                String cpassword = field_cpassword.getEditText().getText().toString();
                String dob = field_dob.getEditText().getText().toString();
                String phone = field_phone.getEditText().getText().toString();
                String semester = field_semester.getEditText().getText().toString();
                String division = field_division.getEditText().getText().toString();
                String branch = field_branch.getEditText().getText().toString();
                String gender_ = "";
                if (male_es.isChecked()) {
                    gender_ = "male";
                } else {
                    gender_ = "female";
                }
                String email_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";
                String phone_pttn = "[6-9][0-9]{9}";
                String semester_pttn = "[1-8]";
                String division_pttn = "^[A-Z]{2}+(-)[A-Z]{1}$";


                if (!TextUtils.isEmpty(fullname)) {
                    field_fullname.setError(null);
                    field_fullname.setErrorEnabled(false);
                    if (!TextUtils.isEmpty(username) && username.length() <= 12) {
                        field_eno.setError(null);
                        field_eno.setErrorEnabled(false);
                        if (!TextUtils.isEmpty(email) && email.matches(email_pttn)) {
                            field_email.setError(null);
                            field_email.setErrorEnabled(false);
                            if (!TextUtils.isEmpty(password) && password.length() <= 10 && password.length() >= 6) {
                                field_password.setError(null);
                                field_password.setErrorEnabled(false);
                                if (!TextUtils.isEmpty(cpassword) && cpassword.matches(password)) {
                                    field_cpassword.setError(null);
                                    field_cpassword.setErrorEnabled(false);
                                    if (!TextUtils.isEmpty(dob)) {
                                        field_dob.setError(null);
                                        field_dob.setErrorEnabled(false);
                                        if (!TextUtils.isEmpty(phone) && phone.matches(phone_pttn)) {
                                            field_phone.setError(null);
                                            field_phone.setErrorEnabled(false);
                                            if (!TextUtils.isEmpty(semester) && semester.matches(semester_pttn)) {
                                                field_semester.setError(null);
                                                field_semester.setErrorEnabled(false);
                                                if (!TextUtils.isEmpty(division) && division.matches(division_pttn)) {
                                                    field_division.setError(null);
                                                    field_division.setErrorEnabled(false);
                                                    if (!TextUtils.isEmpty(branch)) {
                                                        field_branch.setError(null);
                                                        field_branch.setErrorEnabled(false);
                                                        storageReference = FirebaseStorage.getInstance().getReference("Images").child("Students").child(username);

                                                        String finalGender_1 = gender_;
                                                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                    @Override
                                                                    public void onSuccess(Uri uri) {
                                                                        Student_Model Student_Model = new Student_Model(userid,fullname, username, email, password, finalGender_1, dob, phone, semester, division, branch, uri.toString());
                                                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                loadingbar.setVisibility(View.VISIBLE);
                                                                                databaseReference.setValue(Student_Model);
                                                                                Toast.makeText(Edit_Student.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                                startActivity(new Intent(Edit_Student.this, Student_List.class));
                                                                                finishAndRemoveTask();
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    } else {
                                                        field_branch.setError("Please Select Your Branch");
                                                    }
                                                } else {
                                                    field_division.setError("Invalid Class");
                                                }
                                            } else {
                                                field_semester.setError("Semester Must be 1-8");
                                            }
                                        } else {
                                            field_phone.setError("Invalid Phone Number");
                                        }
                                    } else {
                                        field_dob.setError("Please Enter Your Date Of Birth");
                                    }
                                } else {
                                    field_cpassword.setError("Password did not Match");
                                }
                            } else {
                                field_password.setError("Password Must Be 6-8");
                            }
                        } else {
                            field_email.setError("Invalid Email Address");
                        }
                    } else {
                        field_eno.setError("Invalid Enrollment Number");
                    }
                } else {
                    field_fullname.setError("Please Enter Your Fullname");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            profile_es.setImageURI(uri);
        }
    }
}