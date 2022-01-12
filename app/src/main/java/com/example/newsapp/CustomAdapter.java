package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<NewsHeadlines> newsHeadlinesList;
    private SelectListener listener;

    public CustomAdapter(Context context, List<NewsHeadlines> newsHeadlinesList,SelectListener listener) {
        this.context = context;
        this.newsHeadlinesList = newsHeadlinesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.txt_title.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        holder.txt_source.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        holder.img_headline.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animation));

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animation));

        // Add objects of the news headline
        holder.txt_title.setText(newsHeadlinesList.get(position).getTitle());
        holder.txt_source.setText(newsHeadlinesList.get(position).getSource().getName());

        if(newsHeadlinesList.get(position).getUrlToImage() != null){
            Picasso.get().load(newsHeadlinesList.get(position).getUrlToImage()).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnNewsClicked(newsHeadlinesList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsHeadlinesList.size();
    }
}
