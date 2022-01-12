package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {
    WebView webView;
    SwipeRefreshLayout swipe;
    NewsHeadlines headlines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");
        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                WebAction();
            }
        });
        WebAction();
        /*        title = findViewById(R.id.txt_title_source);
                author = findViewById(R.id.txt_author);
                content = findViewById(R.id.txt_content);
                time = findViewById(R.id.txt_time);
                //detail = findViewById(R.id.txt_title_source);
                source = findViewById(R.id.img_source)
                title.setText(headlines.getTitle());
                author.setText(headlines.getAuthor());
                content.setText(headlines.getContent());
                time.setText(headlines.getPublishedAt());
                Picasso.get().load(headlines.getUrlToImage()).into(source);*/

    }
    public void WebAction(){

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(headlines.getUrl());
        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                webView.loadUrl("file:///android_assets/error.html");

            }
            public void onPageFinished(WebView view, String url) {
                swipe.setRefreshing(false);
            }

        });

    }

    @Override
    public void onBackPressed(){

        if (webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }
}