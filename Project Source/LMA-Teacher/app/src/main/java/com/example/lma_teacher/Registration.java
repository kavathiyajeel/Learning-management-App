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
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Objects;

public class Registration extends AppCompatActivity {
    private TextInputLayout fullname_var, username_var, email_var, pass_var, cpass_var, dob_var, phone_var, graduation_var;
    private RadioButton rm;
    private TextInputEditText dob_txt;
    private Button register;
    private ImageView pp;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    Uri uri;
    private DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.bg));
        pp = findViewById(R.id.re_profile);
        fullname_var = findViewById(R.id.fullname_field);
        username_var = findViewById(R.id.username_field);
        email_var = findViewById(R.id.email_field);
        pass_var = findViewById(R.id.pass_field);
        cpass_var = findViewById(R.id.cpass_field);
        dob_var = findViewById(R.id.dob_field);
        phone_var = findViewById(R.id.phn_field);
        graduation_var = findViewById(R.id.graduation_field);
        rm = findViewById(R.id.rg_male);
        register = findViewById(R.id.reg_btn);
        dob_txt = findViewById(R.id.re_date);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        db = firebaseDatabase.getReference("Teachers");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateData();
            }
        });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dob_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Registration.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
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
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
    }

    //to validate the data
    private void ValidateData() {
        String fullname_ = fullname_var.getEditText().getText().toString();
        String username_ = username_var.getEditText().getText().toString();
        String email_ = email_var.getEditText().getText().toString();
        String pass_ = pass_var.getEditText().getText().toString();
        String cpass = cpass_var.getEditText().getText().toString();
        String dob_ = dob_var.getEditText().getText().toString();
        String phn_ = phone_var.getEditText().getText().toString();
        String grd_ = graduation_var.getEditText().getText().toString();
        String gender_ = "";
        if (rm.isChecked()) {
            gender_ = "male";
        } else {
            gender_ = "female";
        }

        String email_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";
        String phone_pttn = "[6-9][0-9]{9}";

        if (!fullname_.isEmpty()) {
            fullname_var.setError(null);
            fullname_var.setErrorEnabled(false);
            if (!username_.isEmpty()) {
                username_var.setError(null);
                username_var.setErrorEnabled(false);
                if (!email_.isEmpty() && email_.matches(email_pttn)) {
                    email_var.setError(null);
                    email_var.setErrorEnabled(false);
                    if (!pass_.isEmpty()) {
                        pass_var.setError(null);
                        pass_var.setErrorEnabled(false);
                        if (!cpass.isEmpty()) {
                            cpass_var.setError(null);
                            cpass_var.setErrorEnabled(false);
                            if (!dob_.isEmpty()) {
                                dob_var.setError(null);
                                dob_var.setErrorEnabled(false);
                                if (!phn_.isEmpty() && phn_.matches(phone_pttn)) {
                                    phone_var.setError(null);
                                    phone_var.setErrorEnabled(false);
                                    if (!grd_.isEmpty()) {
                                        graduation_var.setError(null);
                                        graduation_var.setErrorEnabled(false);
                                        //Upload data into firebase
                                        uploadTofirebase();
                                    } else {
                                        graduation_var.setError("Please Enter Your Graduation");
                                    }

                                } else {
                                    phone_var.setError("Please Enter Your Phone-Number");
                                }

                            } else {
                                dob_var.setError("Please Enter Yout Date Of Birth");
                            }

                        } else {
                            cpass_var.setError("Please Enter Your Confirm Password");
                        }

                    } else {
                        pass_var.setError("Please Enter Your Password");
                    }

                } else {
                    email_var.setError("Please Enter Your Email");
                }

            } else {
                username_var.setError("Please Enter Your Username");
            }

        } else {
            fullname_var.setError("Please Enter Your Full-Name");
        }
    }

    private void uploadTofirebase() {
        String fullname = fullname_var.getEditText().getText().toString();
        String username = username_var.getEditText().getText().toString();
        String email = email_var.getEditText().getText().toString();
        String pass = pass_var.getEditText().getText().toString();
        String gender = "";
        rm = findViewById(R.id.rg_male);
        if (rm.isChecked()) {
            gender = "male";
        } else {
            gender = "female";
        }

        String dob = dob_var.getEditText().getText().toString();
        String phn = phone_var.getEditText().getText().toString();
        String grd = graduation_var.getEditText().getText().toString();
        storageReference = FirebaseStorage.getInstance().getReference("Images").child("Teachers").child(username);
        String finalGender = gender;
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        User_Model rg = new User_Model(fullname, username, email, pass, finalGender, dob, phn, grd, uri.toString());
                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registration.this, "User Created", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String uid = user.getUid();
                                    db.child(uid).setValue(rg);
                                    Toast.makeText(Registration.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Registration.this, Login.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(Registration.this, "User creation failed", Toast.LENGTH_SHORT).show();
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
            pp.setImageURI(uri);
        }
    }

    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}