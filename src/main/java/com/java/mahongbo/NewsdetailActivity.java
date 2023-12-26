package com.java.mahongbo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.java.mahongbo.signup.MyDatabaseHelper;

//TODO:视频播放功能
public class NewsdetailActivity extends AppCompatActivity  {
    private ImageView imageView;
    private TextView titleView,authorView,dateView,contentView;
    private Button button;
    private LinearLayout collect_news;
    private MyDatabaseHelper helper;
    private String title,author,date,content,image,video;
    private VideoView mVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
        if(video.length()>0){
            setContentView(R.layout.activity_newsdetail);
        }else{
            setContentView(R.layout.activity_newsdetail_novideo);
        }
        helper = new MyDatabaseHelper(this, "UserDB.db", null, 1);

        initview();
        collect_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                //组装数据
                values2.put("news_title", title);
                values2.put("news_date",date);
                values2.put("news_author",author);
                values2.put("news_content", content);
                db.insert("Collection_News", null, values2);
                Toast.makeText(NewsdetailActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                db.close();
            }
        });
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
        image=intent.getStringExtra("image");
        video=intent.getStringExtra("video");
    }
    public void initview(){
        imageView =findViewById(R.id.imageView);
        titleView = findViewById(R.id.tTitle);
        authorView = findViewById(R.id.tPublisher);
        dateView=findViewById(R.id.tDate);
        contentView=findViewById(R.id.content);
        button = findViewById(R.id.back);
        collect_news=findViewById(R.id.collect_news);
        if(video.length()>0){
            initVideoView();
        }
        titleView.setText(title);
        authorView.setText(author);
        dateView.setText(date);
        contentView.setText(content);
        Glide.with(this).load(image).into(imageView);
    }
    void initVideoView(){
        mVideo = (VideoView) findViewById(R.id.videoview);
        //视屏控制器
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideo);
        String uri=video;
        Uri _uri=Uri.parse(uri);
        mVideo.setMediaController(mediaController);
        mVideo.setVideoURI(_uri);
        mVideo.start();
    }



}
