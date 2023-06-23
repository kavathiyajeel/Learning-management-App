package com.example.lma_student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Lecture_List extends AppCompatActivity implements Lecture_Adapter.LectureClick{
    private RecyclerView lecturerv;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Lecture_Model> lecture_modelArrayList;
    private Lecture_Adapter lecture_adapter;
    private Day_Model day_model;
    private Class_Model class_model;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle("TimeTable");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));

        firebaseDatabase = FirebaseDatabase.getInstance();


        lecturerv = findViewById(R.id.LectureRV);
        loading = findViewById(R.id.lecture_loading);
        loading.setVisibility(View.VISIBLE);
        class_model = getIntent().getParcelableExtra("Class");
        day_model = getIntent().getParcelableExtra("Day");
        lecture_modelArrayList = new ArrayList<>();
        lecture_adapter = new Lecture_Adapter(lecture_modelArrayList,this,this);
        lecturerv.setLayoutManager(new LinearLayoutManager(this));
        lecturerv.setAdapter(lecture_adapter);
        databaseReference = firebaseDatabase.getReference("Timetable").child(class_model.getDiv()).child("content").child(day_model.getDay()).child("content");
        getData();
    }

    private void getData() {
        lecture_modelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                lecture_modelArrayList.add(snapshot.getValue(Lecture_Model.class));
                lecture_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                lecture_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);
                lecture_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                lecture_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);
                lecture_adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLectureClick(int position) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}