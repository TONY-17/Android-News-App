package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.SearchEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.newsapp.Models.NewsApiResponse;
import com.example.newsapp.Models.NewsHeadlines;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements  SelectListener {
    SearchView searchView;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ProgressDialog dialog;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dialog = new ProgressDialog(this);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Searching for " + query);
                dialog.show();
                RequestManager manager = new RequestManager(SearchActivity.this);
                manager.getNewsHeadlines(listener,"general",query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        back = findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(SearchActivity.this,"NO data ",Toast.LENGTH_SHORT);
            }else {
                showNews(list);
                dialog.dismiss();

            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(SearchActivity.this,"An error occured ",Toast.LENGTH_SHORT);
        }
    };

    public void showNews(List<NewsHeadlines> list){
        recyclerView = findViewById(R.id.dynamic_);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        customAdapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(customAdapter);

    }
    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        Intent intent = new Intent(SearchActivity.this, NewsActivity.class);
        startActivity(intent.putExtra("data",headlines));
    }
}