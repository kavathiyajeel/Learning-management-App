package com.example.lma_teacher;

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

public class Chapter_Adapter extends RecyclerView.Adapter<Chapter_Adapter.ViewHolder> {
    private ArrayList<Chapter_Model>chapter_modelArrayList;
    private Context context;
    private ChapterClick chapterClick;
    int lastpos = - 1;

    public Chapter_Adapter(ArrayList<Chapter_Model> chapter_modelArrayList, Context context, ChapterClick chapterClick) {
        this.chapter_modelArrayList = chapter_modelArrayList;
        this.context = context;
        this.chapterClick = chapterClick;
    }

    @NonNull
    @Override
    public Chapter_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        setAnimation(holder.itemView,position);
        Chapter_Model chapter_model = chapter_modelArrayList.get(position);
        holder.title.setText(chapter_model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chapterClick.onChapterClick(position);
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
        return chapter_modelArrayList.size();
    }
    public interface ChapterClick{
        void onChapterClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.chp_name);
        }
    }
}
