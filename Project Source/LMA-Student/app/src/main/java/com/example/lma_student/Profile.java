package com.example.lma_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Objects;

public class Profile extends AppCompatActivity {
    private TextView fullname,username,email,gender,dob,phone,semester,division,branch;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Profile_Model profile_model;
    private ProgressBar loading;
    private FirebaseAuth mAuth;
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //SupportActionBar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));

        loading = findViewById(R.id.vp_loading);
        loading.setVisibility(View.VISIBLE);

        fullname = findViewById(R.id.vp_fullname);
        username = findViewById(R.id.vp_username);
        email = findViewById(R.id.vp_email);
        gender = findViewById(R.id.vp_gender);
        dob = findViewById(R.id.vp_dob);
        phone = findViewById(R.id.vp_contact);
        semester = findViewById(R.id.vp_semester);
        division = findViewById(R.id.vp_division);
        branch = findViewById(R.id.vp_branch);
        profile = findViewById(R.id.profile);
    }
@Override
protected void onStart() {
firebaseDatabase = FirebaseDatabase.getInstance();
mAuth = FirebaseAuth.getInstance();
String Userid = mAuth.getCurrentUser().getUid().toString();
databaseReference = firebaseDatabase.getReference("Students").child(Userid);
databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        loading.setVisibility(View.GONE);
        profile_model = snapshot.getValue(Profile_Model.class);
        fullname.setText(profile_model.getFullname());
        username.setText(profile_model.getUsername());
        email.setText(profile_model.getEmail());
        gender.setText(profile_model.getGender());
        dob.setText(profile_model.getDob());
        phone.setText(profile_model.getPhone());
        semester.setText(profile_model.getSemester());
        division.setText(profile_model.getDivision());
        branch.setText(profile_model.getBranch());
        Picasso.get().load(profile_model.getImage()).resize(300,300).into(profile);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        loading.setVisibility(View.GONE);
        Toast.makeText(Profile.this, "error "+error.toString(), Toast.LENGTH_SHORT).show();

    }
});
    super.onStart();
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}