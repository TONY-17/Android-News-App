package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.newsapp.Models.NewsApiResponse;
import com.example.newsapp.Models.NewsHeadlines;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  SelectListener , View.OnClickListener{
    ProgressDialog dialog;
    ImageButton search;

    MaterialButton general;
    MaterialButton business;
    MaterialButton entertainment;
    MaterialButton health;
    MaterialButton science;
    MaterialButton sports;
    MaterialButton technology;


    MaterialCardView refresh;

    ShimmerFrameLayout shimmerFrameLayout;


    TextView logo;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = findViewById(R.id.refresh);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);

        general = findViewById(R.id.general);
        general.setOnClickListener(this);
        business = findViewById(R.id.business);
        business.setOnClickListener(this);
        entertainment = findViewById(R.id.entertainment);
        entertainment.setOnClickListener(this);
        health = findViewById(R.id.health);
        health.setOnClickListener(this);
        science = findViewById(R.id.science);
        science.setOnClickListener(this);
        sports = findViewById(R.id.sports);
        sports.setOnClickListener(this);
        technology = findViewById(R.id.technology);
        technology.setOnClickListener(this);



        search = findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(i);
            }
        });


        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news");
        dialog.show();
        Refresh();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Refreshing news");
                dialog.show();
                Refresh();
            }
        });

    }

    public void Refresh(){
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);
    }
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);

            dialog.dismiss();
        }

        @Override
        public void onError(String message) {
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    };
    public void showNews(List<NewsHeadlines> list){
        recyclerView = findViewById(R.id.recyclerItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        customAdapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(customAdapter);

    }
    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
        startActivity(intent.putExtra("data",headlines));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        button.setBackgroundColor(Color.WHITE);
        String category = button.getText().toString();
        dialog.setTitle("Fetching data for "+ category
        );
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,category,null);

    }
}