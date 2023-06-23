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

public class Class_Adapter extends RecyclerView.Adapter<Class_Adapter.ViewHolder>{
    private ArrayList<Class_Model> class_modelArrayList;
    private Context context;
    private ClassClick classClick;
    int lastpos = -1;

    public Class_Adapter(ArrayList<Class_Model> class_modelArrayList, Context context, ClassClick classClick) {
        this.class_modelArrayList = class_modelArrayList;
        this.context = context;
        this.classClick = classClick;
    }

    @NonNull
    @Override
    public Class_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class_card,parent,false);
        return new Class_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        setAnimation(holder.itemView,position);
        Class_Model class_model = class_modelArrayList.get(position);
        holder.div.setText(class_model.getDiv());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classClick.onClassClick(position);
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
        return class_modelArrayList.size();
    }
    public interface ClassClick{
        void onClassClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView div;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            div = itemView.findViewById(R.id.class_name);
        }
    }
}
