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
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Objects;

public class Add_Student extends AppCompatActivity {
    AutoCompleteTextView branch_txt;
    TextInputLayout fullname_field, eno_field, email_field, password_field, cpassword_field, dob_field, phone_field, semester_field, division_field,branch_field;
    private TextInputEditText fullname_txt, username_txt, email_txt, password_txt, cpassword_txt, dob_txt, phone_txt, semester_txt, division_txt;
    private Button add_btn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RadioButton male_as;
    DatePickerDialog.OnDateSetListener setListener;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    ImageView profile_as;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Add Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));



        fullname_txt = findViewById(R.id.as_fullname);
        username_txt = findViewById(R.id.as_username);
        email_txt = findViewById(R.id.as_email);
        password_txt = findViewById(R.id.as_password);
        cpassword_txt = findViewById(R.id.as_cpassword);
        phone_txt = findViewById(R.id.as_phone);
        dob_txt = findViewById(R.id.as_dob);
        semester_txt = findViewById(R.id.as_semester);
        division_txt = findViewById(R.id.as_division);
        male_as = findViewById(R.id.as_male);
        branch_txt = findViewById(R.id.as_branch);
        add_btn = findViewById(R.id.as_btn);
        profile_as = findViewById(R.id.as_profile);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference("Students");

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dob_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Student.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
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
        profile_as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateDate();
            }

            private void ValidateDate() {

                fullname_field = findViewById(R.id.as_fullname_field);
                eno_field = findViewById(R.id.as_username_field);
                email_field = findViewById(R.id.as_email_field);
                password_field = findViewById(R.id.as_password_field);
                cpassword_field = findViewById(R.id.as_cpassword_field);
                dob_field = findViewById(R.id.as_dob_field);
                phone_field = findViewById(R.id.as_phone_field);
                semester_field = findViewById(R.id.as_semester_field);
                division_field = findViewById(R.id.as_division_field);
                branch_field = findViewById(R.id.as_branch_field);


                String fullname = fullname_field.getEditText().getText().toString();
                String enrollmentno = eno_field.getEditText().getText().toString();
                String email = email_field.getEditText().getText().toString();
                String password = password_field.getEditText().getText().toString();
                String cpassword = cpassword_field.getEditText().getText().toString();
                String dob = dob_field.getEditText().getText().toString();
                String phone = phone_field.getEditText().getText().toString();
                String semester = semester_field.getEditText().getText().toString();
                String division = division_field.getEditText().getText().toString();
                String branch = branch_field.getEditText().getText().toString();
                String gender_ = "";
                if (male_as.isChecked()) {
                    gender_ = "Male";
                } else {
                    gender_ = "Female";
                }
                String email_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";
                String phone_pttn = "[6-9][0-9]{9}";
                String semester_pttn = "[1-8]";
                String division_pttn = "^[A-Z]{2}+(-)[A-Z]{1}$";


                if (!TextUtils.isEmpty(fullname)) {
                    fullname_field.setError(null);
                    fullname_field.setErrorEnabled(false);
                    if (!TextUtils.isEmpty(enrollmentno) && enrollmentno.length() <= 12) {
                        eno_field.setError(null);
                        eno_field.setErrorEnabled(false);
                        if (!TextUtils.isEmpty(email) && email.matches(email_pttn)) {
                            email_field.setError(null);
                            email_field.setErrorEnabled(false);
                            if (!TextUtils.isEmpty(password) && password.length() <= 10 && password.length() >= 6) {
                                password_field.setError(null);
                                password_field.setErrorEnabled(false);
                                if (!TextUtils.isEmpty(cpassword) && cpassword.matches(password)) {
                                    cpassword_field.setError(null);
                                    cpassword_field.setErrorEnabled(false);
                                    if (!TextUtils.isEmpty(dob)) {
                                        dob_field.setError(null);
                                        dob_field.setErrorEnabled(false);
                                        if (!TextUtils.isEmpty(phone) && phone.matches(phone_pttn)) {
                                            phone_field.setError(null);
                                            phone_field.setErrorEnabled(false);
                                            if (!TextUtils.isEmpty(semester) && semester.matches(semester_pttn)) {
                                                semester_field.setError(null);
                                                semester_field.setErrorEnabled(false);
                                                if (!TextUtils.isEmpty(division) && division.matches(division_pttn)) {
                                                    division_field.setError(null);
                                                    division_field.setErrorEnabled(false);
                                                    if (!TextUtils.isEmpty(branch)) {
                                                        branch_field.setError(null);
                                                        branch_field.setErrorEnabled(false);

                                                        uploadTofirebase();
                                                    } else {
                                                        branch_field.setError("Please Select Your Branch");
                                                    }
                                                } else {
                                                    division_field.setError("Invalid Class");
                                                }
                                            } else {
                                                semester_field.setError("Semester must be 1-8");
                                            }
                                        } else {
                                            phone_field.setError("Invalid Phone Number");
                                        }
                                    } else {
                                        dob_field.setError("Please Enter Your Date Of Birth");
                                    }
                                } else {
                                    cpassword_field.setError("Password didn't match");
                                }
                            } else {
                                password_field.setError("Password Must Be 6-8");
                            }
                        } else {
                            email_field.setError("Invalid Email Address");
                        }
                    } else {
                        eno_field.setError("Invalid Enrollment Number");
                    }
                } else {
                    fullname_field.setError("Please Enter Your Fullname");
                }
            }
        });
    }
    private void uploadTofirebase() {
        String fullname = fullname_txt.getText().toString();
        String username = username_txt.getText().toString();
        String email = email_txt.getText().toString();
        String password = password_txt.getText().toString();
        String cpassword = cpassword_txt.getText().toString();
        String dob = dob_txt.getText().toString();
        String phone = phone_txt.getText().toString();
        String semester = semester_txt.getText().toString();
        String division = division_txt.getText().toString();
        String branch = branch_txt.getText().toString();
        //String branch = branch_spinner.getSelectedItem().toString();
        String gender = "";
        male_as = findViewById(R.id.as_male);
        if (male_as.isChecked()) {
            gender = "Male";
        } else {
            gender = "Female";
        }


        String finalGender = gender;
        storageReference = FirebaseStorage.getInstance().getReference("Images").child("Students").child(username);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String userid = mAuth.getCurrentUser().getUid().toString();
                        Student_Model Student_Model = new Student_Model(userid,fullname, username, email, password, finalGender, dob, phone, semester, division, branch, uri.toString());

                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            databaseReference.child(userid).setValue(Student_Model);
                                            Toast.makeText(Add_Student.this, "Students Added..", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Add_Student.this, Add_Student.class));
                                            finishAndRemoveTask();

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(Add_Student.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                        });

                    }
                });
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            profile_as.setImageURI(uri);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}