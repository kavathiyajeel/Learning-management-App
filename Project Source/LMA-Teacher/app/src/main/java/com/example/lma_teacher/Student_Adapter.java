package com.example.lma_teacher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Student_Adapter extends RecyclerView.Adapter<Student_Adapter.ViewHolder> {
    private ArrayList<Student_Model> student_modelArrayList;
    private Context context;
    private StudentClick studentClick;
    int lastpos = -1;

    public Student_Adapter(ArrayList<Student_Model> student_modelArrayList, Context context, StudentClick studentClick) {
        this.student_modelArrayList = student_modelArrayList;
        this.context = context;
        this.studentClick = studentClick;
    }

    @NonNull
    @Override
    public Student_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_addstudents_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        setAnimation(holder.itemView,position);
        Student_Model Student_Model = student_modelArrayList.get(position);
        holder.fullname.setText(Student_Model.getFullname());
        holder.username.setText(Student_Model.getUsername());
        holder.semester.setText("Semester: "+ Student_Model.getSemester());
        holder.division.setText("Class: "+ Student_Model.getDivision());
        Picasso.get().load(Student_Model.getImage()).resize(300,300).into(holder.profilePicture);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentClick.onStudentClick(position);
            }
        });
    }
    private void setAnimation(View itemView,int position){
        if (position>lastpos){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastpos = position;
        }
    }
    @Override
    public int getItemCount() {
        return student_modelArrayList.size();
    }
    public interface StudentClick{
        void onStudentClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView fullname,username,semester,division;
       ImageView profilePicture;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.s_fullname);
            username = itemView.findViewById(R.id.s_username);
            semester = itemView.findViewById(R.id.s_semester);
            division = itemView.findViewById(R.id.s_division);
            profilePicture = itemView.findViewById(R.id.card_profile);
        }
    }
}
