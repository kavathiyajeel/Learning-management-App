package com.example.lma_student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class Day_List extends AppCompatActivity implements Day_Adapter.DayClick{
    private RecyclerView dayrv;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Day_Model> day_modelArrayList;
    private Day_Adapter day_adapter;
    private Class_Model class_model;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle("TimeTable");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));
        dayrv = findViewById(R.id.DayRV);
        loading = findViewById(R.id.day_loading);
        loading.setVisibility(View.VISIBLE);
        class_model = getIntent().getParcelableExtra("Class");
        day_modelArrayList = new ArrayList<>();
        day_adapter = new Day_Adapter(day_modelArrayList,this,this);
        dayrv.setLayoutManager(new LinearLayoutManager(this));
        dayrv.setAdapter(day_adapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Timetable").child(class_model.getDiv().toString()).child("content");
        getData();
    }

    private void getData() {
        day_modelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                day_modelArrayList.add(snapshot.getValue(Day_Model.class));
                day_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                day_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);
                day_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                day_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);
                day_adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDayClick(int position) {
        displaylecture(day_modelArrayList.get(position));
    }

    private void displaylecture(Day_Model day_model) {
        Intent i =new Intent(Day_List.this,Lecture_List.class);
        i.putExtra("Class",class_model);
        i.putExtra("Day",day_model);
        startActivity(i);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}