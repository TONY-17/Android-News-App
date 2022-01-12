package com.example.newsapp;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView txt_title,txt_source;
    ImageView img_headline;
    MaterialCardView cardView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_title = itemView.findViewById(R.id.txt_title);
        txt_source = itemView.findViewById(R.id.txt_source);
        img_headline = itemView.findViewById(R.id.img_headline);

        cardView = itemView.findViewById(R.id.cardView);

    }
}
