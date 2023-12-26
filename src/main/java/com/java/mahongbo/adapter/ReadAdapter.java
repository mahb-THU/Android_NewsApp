package com.java.mahongbo.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.mahongbo.NewsdetailCollectActivity;
import com.java.mahongbo.R;
import com.java.mahongbo.model.NewsData;
import com.java.mahongbo.signup.MyDatabaseHelper;

import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mycontext;
    private MyDatabaseHelper helper;
    private List<NewsData> data;

    public void setData(List<NewsData> data) {
        this.data = data;
    }
    public ReadAdapter(Context context) {
        this.mycontext = context;
    }

    public int getItemViewType(int position) {
        int type = data.get(position).Type();
        return super.getItemViewType(position);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.item_totally_news,parent,false);
        return new ReadAdapter.ViewHolder_First(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsData newsData = data.get(position);
        ReadAdapter.ViewHolder_First viewHolderFirst= (ReadAdapter.ViewHolder_First) holder;
        viewHolderFirst.tTitle.setText(newsData.getTitle());
        viewHolderFirst.tPublisher.setText(newsData.getPublisher());
        viewHolderFirst.tDate.setText(newsData.getPublishTime());
        viewHolderFirst.tipview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mycontext, NewsdetailCollectActivity.class);
                intent.putExtra("title",newsData.getTitle());
                intent.putExtra("author",newsData.getPublisher());
                intent.putExtra("date",newsData.getPublishTime());
                intent.putExtra("content",newsData.getContent());
                intent.putExtra("image",newsData.getImage());
                helper = new MyDatabaseHelper(mycontext, "UserDB.db", null, 1);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                //组装数据
                values2.put("news_title",newsData.getTitle() );
                values2.put("news_date",newsData.getPublishTime());
                values2.put("news_author",newsData.getPublisher());
                values2.put("news_content", newsData.getContent());
                db.insert("Read_News", null, values2);
                Toast.makeText(mycontext, "已保存浏览记录！", Toast.LENGTH_SHORT).show();
                db.close();
                mycontext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if(data == null) {
            return 0;
        }else {
            return data.size();
        }
    }

    public static class ViewHolder_First extends RecyclerView.ViewHolder {
        private TextView tTitle;
        private TextView tDate;
        private TextView tPublisher;
        private LinearLayout tipview;
        public ViewHolder_First(@NonNull View view) {
            super(view);
            tTitle = view.findViewById(R.id.tTitle);
            tDate = view.findViewById(R.id.tDate);
            tPublisher = view.findViewById(R.id.tPublisher);
            tipview=view.findViewById(R.id.onetalk);
        }
    }



}
