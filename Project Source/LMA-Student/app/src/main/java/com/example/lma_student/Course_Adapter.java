package com.example.lma_student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Course_Adapter extends RecyclerView.Adapter<Course_Adapter.ViewHolder>{
    private ArrayList<Course_Model> course_modelArrayList;
    private Context context;
    private CourseClick courseClick;
    int lastpos= -1;

    public Course_Adapter(ArrayList<Course_Model> course_modelArrayList, Context context, CourseClick courseClick) {
        this.course_modelArrayList = course_modelArrayList;
        this.context = context;
        this.courseClick = courseClick;
    }

    @NonNull
    @Override
    public Course_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        setAnimation(holder.itemView,position);
        Course_Model course_model = course_modelArrayList.get(position);
        holder.subname.setText("Name : "+course_model.getName());
        holder.subcode.setText("Code : "+course_model.getCode());
        holder.faculty.setText("Faculty : "+course_model.getFaculty());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseClick.onCourseClick(position);
            }
        });
    }
    private void setAnimation(View itemView, int position) {
        if(position > lastpos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastpos = position;
        }
    }
    @Override
    public int getItemCount() {
        return course_modelArrayList.size();
    }

    public interface CourseClick{
        void onCourseClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView faculty,subname,subcode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            faculty = itemView.findViewById(R.id.mc_fac_name);
            subcode = itemView.findViewById(R.id.mc_sub_code);
            subname = itemView.findViewById(R.id.mc_sub_name);
        }
    }
}
