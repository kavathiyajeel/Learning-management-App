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

public class Lecture_Adapter extends RecyclerView.Adapter<Lecture_Adapter.ViewHolder>{
    private ArrayList<Lecture_Model> lecture_modelArrayList;
    private Context context;
    private LectureClick lectureClick;
    int lastpos = -1;

    public Lecture_Adapter(ArrayList<Lecture_Model> lecture_modelArrayList, Context context, LectureClick lectureClick) {
        this.lecture_modelArrayList = lecture_modelArrayList;
        this.context = context;
        this.lectureClick = lectureClick;
    }
    @NonNull
    @Override
    public Lecture_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lecture_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Lecture_Model lecture_model = lecture_modelArrayList.get(position);
        holder.lecno.setText(lecture_model.getLec_no());
        holder.sub_name.setText("Sub : " + lecture_model.getSub_name());
        holder.sub_code.setText("Code : " + lecture_model.getSub_code());
        holder.fac_name.setText("Faculty: " + lecture_model.getFaculty());
        holder.time.setText("Time : " + lecture_model.getTime());
        setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectureClick.onLectureClick(position);
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
        return lecture_modelArrayList.size();
    }
    public interface LectureClick{
        void onLectureClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lecno, sub_name, sub_code, fac_name, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lecno = itemView.findViewById(R.id.ttd_lecno);
            sub_name = itemView.findViewById(R.id.ttd_sub_name);
            sub_code = itemView.findViewById(R.id.ttd_sub_code);
            fac_name = itemView.findViewById(R.id.ttd_fac_name);
            time = itemView.findViewById(R.id.ttd_time);
        }
    }
}
