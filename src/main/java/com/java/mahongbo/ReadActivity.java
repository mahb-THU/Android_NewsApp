package com.java.mahongbo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.mahongbo.adapter.ReadAdapter;
import com.java.mahongbo.model.NewsData;
import com.java.mahongbo.signup.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    private MyDatabaseHelper helper;
    private RecyclerView recyclerView;
    private Button back;

    private ReadAdapter readAdapter;

    List<NewsData> newsData = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        recyclerView = findViewById(R.id.recyclerView);
        back=findViewById(R.id.willback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        helper = new MyDatabaseHelper(this, "UserDB.db", null, 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        readAdapter = new ReadAdapter(this);
        recyclerView.setAdapter(readAdapter);
        getNewsList();
    }
    private void getNewsList(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Read_News", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    //遍历Cursor对象，取出数据并打印
                    String news_title = cursor.getString(cursor.getColumnIndexOrThrow("news_title"));
                    String news_date = cursor.getString(cursor.getColumnIndexOrThrow("news_date"));
                    String news_author = cursor.getString(cursor.getColumnIndexOrThrow("news_author"));
                    String news_content = cursor.getString(cursor.getColumnIndexOrThrow("news_content"));
                    NewsData news=new NewsData("",news_date,news_author,news_title,news_content);
                    newsData.add(news);
                } while (cursor.moveToNext());
                List<NewsData> news_Data = new ArrayList<>();
                for(int i=0;i<newsData.size();i++){
                    news_Data.add(newsData.get(newsData.size()-1-i));
                }
                readAdapter.setData(news_Data);
            }else{
                Toast.makeText(getApplicationContext(), "浏览记录为空！", Toast.LENGTH_SHORT).show();
            }
            readAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }
}
