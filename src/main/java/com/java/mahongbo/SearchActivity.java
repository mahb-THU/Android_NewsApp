package com.java.mahongbo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.java.mahongbo.adapter.NewsAdapter;
import com.java.mahongbo.api.Api;
import com.java.mahongbo.model.NewsData;
import com.java.mahongbo.model.NewsWhole;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
public class SearchActivity extends AppCompatActivity {
    private RefreshLayout rfL;
    private EditText categories,startdate,enddate,word;
    private Button  gosearch,reback;
    private RecyclerView recyclerView;

    private NewsAdapter newsAdapter;
    private int Num = 1;
    List<NewsData> newsData = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initview();
        initdata();
    }
    private void getNews(String size,String startDate,String endDate,String words,String categories,String page,boolean isRefresh) {
        Call<NewsWhole> callback = Api.getInstance().getApi().getCall(size,startDate,endDate,words,categories,page);
        callback.enqueue(new Callback<NewsWhole>() {
            @Override
            public void onResponse(Call<NewsWhole> call, retrofit2.Response<NewsWhole> anw) {
                if(isRefresh) {
                    rfL.finishRefresh(true);
                } else {
                    rfL.finishLoadMore(true);
                }
                if(anw.isSuccessful()){
                    List<NewsData> list = anw.body().getData();
                    if(list != null && list.size() > 0) {
                        if (isRefresh) {
                            newsData = list;
                        } else {
                            newsData.addAll(list);
                        }
                        newsAdapter.setData(newsData);
                        newsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(SearchActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<NewsWhole> call, Throwable t) {
                if(isRefresh) {
                    rfL.finishRefresh(true);
                } else {
                    rfL.finishLoadMore(true);
                }
                Toast.makeText(SearchActivity.this,"获取新闻列表失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
    void initview(){
        recyclerView=findViewById(R.id.Recycler_View);
        gosearch=findViewById(R.id.gosearch);
        reback=findViewById(R.id.reback);
        categories=findViewById(R.id.categories);
        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        word=findViewById(R.id.word);
        rfL=findViewById(R.id.refreshLayout);
    }
    void initdata(){
        reback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cate=categories.getText().toString();
                String start=startdate.getText().toString();
                String end=enddate.getText().toString();
                String key=word.getText().toString();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                newsAdapter = new NewsAdapter(SearchActivity.this);
                recyclerView.setAdapter(newsAdapter);
                rfL.setOnRefreshListener(new OnRefreshListener() {
                    @Override
                    public void onRefresh(RefreshLayout rfl) {
                        rfl.finishLoadMore(2000/*,false*/);
                        Num = 1;
                        getNews( String.valueOf(15),start,end,key,cate,String.valueOf(1),true);
                    }
                });

                rfL.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(RefreshLayout rfl) {
                        rfl.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                        Num+=1;
                        getNews( String.valueOf(15),start,end,key,cate,String.valueOf(Num),false);
                    }
                });
                getNews( String.valueOf(15),start,end,key,cate,String.valueOf(1),true);
            }
        });
    }
}
