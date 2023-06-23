package com.example.lma_teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.Objects;

public class Manage_Profile extends AppCompatActivity {
    private TextView username,fullname,email,gender,dob,phn,gradu;
    private Button ep;
    private ImageView profilePicture;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private User_Model userModel;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);
        //SupportActionBar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Manage profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));

        loading = findViewById(R.id.mp_loading);
        loading.setVisibility(View.VISIBLE);
        //getting userid from authentication
        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getCurrentUser().getUid().toString();
        ep = findViewById(R.id.get_btn);
        ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Manage_Profile.this, Edit_Profile.class);
                i.putExtra("uid",userid);
                i.putExtra("data",userModel);
                startActivity(i);

            }
        });
        // Fetching the textviews
        username = findViewById(R.id.mp_username);
        fullname = findViewById(R.id.mp_fullname);
        email = findViewById(R.id.mp_email);
        gender = findViewById(R.id.mp_gender);
        dob = findViewById(R.id.mp_dob);
        phn = findViewById(R.id.mp_contact);
        gradu = findViewById(R.id.mp_graduation);
        profilePicture = findViewById(R.id.mp_profile);
    }

    @Override
    protected void onStart() {
        //getting vlaue from realtime database of particular user
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getCurrentUser().getUid().toString();
        databaseReference = firebaseDatabase.getReference("Teachers").child(userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);
                userModel= snapshot.getValue(User_Model.class);
                username.setText(userModel.getUsername());
                fullname.setText(userModel.getFullname());
                email.setText(userModel.getEmail());
                gender.setText(userModel.getGender());
                dob.setText(userModel.getDob());
                phn.setText(userModel.getCno());
                gradu.setText(userModel.getGraduation());
                Picasso.get().load(userModel.getImage()).resize(300,300).into(profilePicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);
                Toast.makeText(Manage_Profile.this, "error "+error.toString(), Toast.LENGTH_SHORT).show();
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