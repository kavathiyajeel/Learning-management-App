package com.example.lma_student;

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

public class Day_Adapter extends RecyclerView.Adapter<Day_Adapter.ViewHolder>{
    private ArrayList<Day_Model> day_modelArrayList;
    private Context context;
    private DayClick dayClick;
    int lastpos = -1;

    public Day_Adapter(ArrayList<Day_Model> day_modelArrayList, Context context, DayClick dayClick) {
        this.day_modelArrayList = day_modelArrayList;
        this.context = context;
        this.dayClick = dayClick;
    }
    @NonNull
    @Override
    public Day_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day_card,parent,false);
        return new Day_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setAnimation(holder.itemView,position);
        Day_Model day_model = day_modelArrayList.get(position);
        holder.day.setText(day_model.getDay());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayClick.onDayClick(position);
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
        return day_modelArrayList.size();
    }

    public interface DayClick{
        void onDayClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day_name);
        }
    }
}
