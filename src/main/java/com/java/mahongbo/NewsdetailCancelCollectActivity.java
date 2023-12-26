package com.java.mahongbo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.java.mahongbo.signup.MyDatabaseHelper;

public class NewsdetailCancelCollectActivity extends AppCompatActivity {
    private TextView titleView,authorView,dateView,contentView;
    private Button button,cancel;
    private String title,author,date,content;
    private MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail_cancelcollect);
        initdata();
        myDatabaseHelper =new MyDatabaseHelper(this,"UserDB.db",null,1);
        initview();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db= myDatabaseHelper.getWritableDatabase();
                db.delete("Collection_News","news_title = ?",new String[]{title});
                Toast.makeText(NewsdetailCancelCollectActivity.this,"取消收藏成功",Toast.LENGTH_SHORT).show();
                db.close();
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
        cancel=findViewById(R.id.cancel);
        titleView.setText(title);
        authorView.setText(author);
        dateView.setText(date);
        contentView.setText(content);
    }
}
