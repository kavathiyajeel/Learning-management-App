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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
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

public class Edit_Profile extends AppCompatActivity {
    private TextInputEditText user, fname, email, dob, phn, gradu;
    private TextInputLayout fullname_var, username_var, email_var, pass_var, cpass_var, dob_var, phone_var, graduation_var;
    private RadioButton rm, rf;
    private MaterialButton ep;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private User_Model userModel;
    private ProgressBar loading;
    private ImageView profile;
    private StorageReference storageReference;
    Uri uri;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //SupportAction bar
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.bg));
        //progressbar id
        loading = findViewById(R.id.ep_loading);

// edit-text id's
        user = findViewById(R.id.ep_username);
        fname = findViewById(R.id.ep_fullname);
        email = findViewById(R.id.ep_email);
        rm = findViewById(R.id.ep_male);
        rf = findViewById(R.id.ep_female);
        dob = findViewById(R.id.ep_dob);
        phn = findViewById(R.id.ep_phn);
        gradu = findViewById(R.id.ep_gradu);
        profile = findViewById(R.id.ep_profile);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Edit_Profile.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                month = month + 1;
                String date = dayofMonth + "/" + month + "/" + year;
                dob.setText(date);

            }
        };

// TextInput layout id's
        fullname_var = findViewById(R.id.ep_fullname_field);
        username_var = findViewById(R.id.ep_username_field);
        email_var = findViewById(R.id.ep_email_field);
        dob_var = findViewById(R.id.ep_dob_field);
        phone_var = findViewById(R.id.ep_phn_field);
        graduation_var = findViewById(R.id.ep_graduation_field);
        ep = findViewById(R.id.mp_update_btn);
        //getting data and set into Edit-text boxes
        Intent i = getIntent();
        userModel = getIntent().getParcelableExtra("data");
        user.setText(userModel.getUsername());
        fname.setText(userModel.getFullname());
        email.setText(userModel.getEmail());
        Picasso.get().load(userModel.getImage()).resize(120, 120).into(profile);


        String gender = userModel.getGender();
        if (gender.equals("male")) {
            rm.setChecked(true);
        } else {
            rf.setChecked(true);
        }

        dob.setText(userModel.getDob());
        phn.setText(userModel.getCno());
        gradu.setText(userModel.getGraduation());
        String userid = i.getStringExtra("uid");
        firebaseDatabase = FirebaseDatabase.getInstance();
// getting reference
        db = firebaseDatabase.getReference("Teachers").child(userid);
        //edit profile button event listner
        ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateData();

            }

            private void validateData() {
                // getting the data from edit-text and storing into variables
                String fullname, username, email, pass, gender_, dob, cno, graduation;
                fullname = fullname_var.getEditText().getText().toString();
                username = username_var.getEditText().getText().toString();
                pass = userModel.getPass();
                String email_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";
                String phn_pttn = "[6-9][0-9]{9}";
                email = email_var.getEditText().getText().toString();
                graduation = graduation_var.getEditText().getText().toString();
                gender_ = "";
                if (rm.isChecked()) {
                    gender_ = "Male";
                } else {
                    gender_ = "Female";
                }
                dob = dob_var.getEditText().getText().toString();
                cno = phone_var.getEditText().getText().toString();


//validating the data
                if (!TextUtils.isEmpty(username)) {
                    username_var.setError(null);
                    username_var.setErrorEnabled(false);
                    if (!TextUtils.isEmpty(fullname)) {
                        fullname_var.setError(null);
                        fullname_var.setErrorEnabled(false);
                        if (!TextUtils.isEmpty(email) && email.matches(email_pttn)) {
                            email_var.setError(null);
                            email_var.setErrorEnabled(false);
                            if (!TextUtils.isEmpty(dob)) {
                                dob_var.setError(null);
                                dob_var.setErrorEnabled(false);
                                if (!TextUtils.isEmpty(cno) && cno.matches(phn_pttn)) {
                                    phone_var.setError(null);
                                    phone_var.setErrorEnabled(false);
                                    if (!TextUtils.isEmpty(graduation)) {
                                        graduation_var.setError(null);
                                        graduation_var.setErrorEnabled(false);
                                        //updating data using add value event listener
//db.addListenerForSingleValueEvent(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//        loading.setVisibility(View.VISIBLE);
//        db.updateChildren(map);
//        Toast.makeText(EditProfile.this, "Updated", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(EditProfile.this, ManageProfile.class));
//        finishAndRemoveTask();
//
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//        loading.setVisibility(View.VISIBLE);
//        Toast.makeText(EditProfile.this, "Failed", Toast.LENGTH_SHORT).show();
//    }
//});
                                        storageReference = FirebaseStorage.getInstance().getReference("Images").child("Teachers").child(username);
                                        String finalGender_ = gender_;
                                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        User_Model userModel = new User_Model(fullname, username, email, pass, finalGender_, dob, cno, graduation, uri.toString());
                                                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                loading.setVisibility(View.VISIBLE);
                                                                db.setValue(userModel);
                                                                Toast.makeText(Edit_Profile.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(Edit_Profile.this, Manage_Profile.class));
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
                                        graduation_var.setError("Graduation cannot be empty");
                                    }
                                } else {
                                    phone_var.setError("Invalid phone number");
                                }
                            } else {
                                dob_var.setError("Date of Birth cannot be empty");
                            }
                        } else {
                            email_var.setError("Invalid email address");
                        }
                    } else {
                        fullname_var.setError("Fullname cannot be empty");
                    }
                } else {
                    username_var.setError("username cannot be empty");
                }

            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            profile.setImageURI(uri);
        }
    }

    // back to previous activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}