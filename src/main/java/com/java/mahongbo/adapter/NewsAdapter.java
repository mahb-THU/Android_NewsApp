package com.java.mahongbo.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.java.mahongbo.NewsdetailActivity;
import com.java.mahongbo.R;
import com.java.mahongbo.model.NewsData;
import com.java.mahongbo.signup.MyDatabaseHelper;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mycontext;
    private MyDatabaseHelper helper;
    private List<NewsData> data;

    public void setData(List<NewsData> data) {
        this.data = data;
    }
    public NewsAdapter(Context context) {
        this.mycontext = context;
    }

    public int getItemViewType(int pos) {
        int type = data.get(pos).Type();
        return type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
            View view = LayoutInflater.from(mycontext).inflate(R.layout.item_image_news,parent,false);
            return new ViewHolderFirst(view);
        }else{
            View view = LayoutInflater.from(mycontext).inflate(R.layout.item_totally_news,parent,false);
            return new ViewHolderSecond(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsData newsData = data.get(position);
        helper = new MyDatabaseHelper(mycontext, "UserDB.db", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        if(newsData.Type()==1){
            ViewHolderFirst vhfirst = (ViewHolderFirst) holder;
            vhfirst.tTitle.setText(newsData.getTitle());
            vhfirst.tPublisher.setText(newsData.getPublisher());
            vhfirst.tDate.setText(newsData.getPublishTime());
            if(existdata(newsData.getTitle())){
                vhfirst.tTitle.setTextColor(Color.parseColor("#808080"));
            }
            String url = newsData.getImage().substring(1,newsData.getImage().length()-1);
            String[] buff=url.split(",");
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.nopicture);
            Glide.with(mycontext).load(buff[0]).apply(requestOptions).into(vhfirst.tImage);
            vhfirst.tipview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mycontext, NewsdetailActivity.class);
                    intent.putExtra("title",newsData.getTitle());
                    intent.putExtra("author",newsData.getPublisher());
                    intent.putExtra("date",newsData.getPublishTime());
                    intent.putExtra("content",newsData.getContent());
                    intent.putExtra("image",buff[0]);
                    intent.putExtra("video",newsData.getVideo());
                    savedata(newsData);
                    if(existdata(newsData.getTitle())){
                        vhfirst.tTitle.setTextColor(Color.parseColor("#808080"));
                    }
                    mycontext.startActivity(intent);
                    //TODO:视频播放功能
                }
            });
        }else{
            ViewHolderSecond vhsecond = (ViewHolderSecond) holder;
            vhsecond.tTitle.setText(newsData.getTitle());
            vhsecond.tPublisher.setText(newsData.getPublisher());
            if(existdata(newsData.getTitle())){
                vhsecond.tTitle.setTextColor(Color.parseColor("#808080"));
            }
            vhsecond.tDate.setText(newsData.getPublishTime());
            vhsecond.tipview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mycontext, NewsdetailActivity.class);
                    intent.putExtra("title",newsData.getTitle());
                    intent.putExtra("author",newsData.getPublisher());
                    intent.putExtra("date",newsData.getPublishTime());
                    intent.putExtra("content",newsData.getContent());
                    intent.putExtra("video",newsData.getVideo());
                    savedata(newsData);
                    if(existdata(newsData.getTitle())){
                        vhsecond.tTitle.setTextColor(Color.parseColor("#808080"));
                    }
                    mycontext.startActivity(intent);
                }
            });
        }
        db.close();
    }
    @Override
    public int getItemCount() {
        if(data == null) {
            return 0;
        }else {
            return data.size();
        }
    }

    public static class ViewHolderFirst extends RecyclerView.ViewHolder {
        private TextView tTitle;
        private TextView tDate;
        private TextView tPublisher;
        private ImageView tImage;
        private LinearLayout tipview;
        public ViewHolderFirst(@NonNull View view) {
            super(view);
            tTitle = view.findViewById(R.id.tvTitle);
            tDate = view.findViewById(R.id.tvDate);
            tPublisher = view.findViewById(R.id.tvPublisher);
            tImage = view.findViewById(R.id.imageView);
            tipview=view.findViewById(R.id.onechat);
        }
    }
    public static class ViewHolderSecond extends RecyclerView.ViewHolder {
        private TextView tTitle;
        private TextView tDate;
        private TextView tPublisher;
        private LinearLayout tipview;
        public ViewHolderSecond(@NonNull View view) {
            super(view);
            tTitle = view.findViewById(R.id.tTitle);
            tDate = view.findViewById(R.id.tDate);
            tPublisher = view.findViewById(R.id.tPublisher);
            tipview=view.findViewById(R.id.onetalk);
        }
    }
    void savedata(NewsData newsData){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.beginTransaction();
        ContentValues values2 = new ContentValues();
        //组装数据
        values2.put("news_title",newsData.getTitle() );
        values2.put("news_date",newsData.getPublishTime());
        values2.put("news_author",newsData.getPublisher());
        values2.put("news_content", newsData.getContent());
        db.insert("Read_News", null, values2);
        Toast.makeText(mycontext, "已保存浏览记录！", Toast.LENGTH_SHORT).show();
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    boolean existdata(String title){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.beginTransaction();
        Cursor cursor=db.rawQuery("select * from Read_News where news_title =?", new String[]{title});
        if(cursor.getCount()>0){
            db.setTransactionSuccessful();
            db.endTransaction();
            cursor.close();
            return true;
        }else {
            db.setTransactionSuccessful();
            db.endTransaction();
            cursor.close();
            return false;
        }
    }
}
