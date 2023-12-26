package com.java.mahongbo;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//TODO:视频播放功能
public class NewsdetailCollectActivity extends AppCompatActivity {
    private TextView titleView,authorView,dateView,contentView;
    private Button button;
    private String title,author,date,content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetailcollect);
        initdata();
        initview();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initdata(){
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        author = intent.getStringExtra("author");
        date=intent.getStringExtra("date");
        content=intent.getStringExtra("content");
    }
    public void initview(){
        titleView = findViewById(R.id.tTitle);
        authorView = findViewById(R.id.tPublisher);
        dateView=findViewById(R.id.tDate);
        contentView=findViewById(R.id.content);
        button = findViewById(R.id.back);
        titleView.setText(title);
        authorView.setText(author);
        dateView.setText(date);
        contentView.setText(content);
    }
}
