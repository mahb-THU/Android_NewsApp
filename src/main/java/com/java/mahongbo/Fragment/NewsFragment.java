package com.java.mahongbo.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.java.mahongbo.R;
import com.java.mahongbo.adapter.NewsAdapter;
import com.java.mahongbo.api.Api;
import com.java.mahongbo.model.NewsData;
import com.java.mahongbo.model.NewsWhole;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NewsFragment extends Fragment {
    private RefreshLayout rfL;
    private String cate;
    private RecyclerView recyclerView;

    private NewsAdapter newsAdapter;
    private int Num = 1;

    List<NewsData> newsData = new ArrayList<>();
    public NewsFragment() {

    }

    public static NewsFragment newInstance(String cate) {
        NewsFragment fr = new NewsFragment();
        if(cate=="头条"){
            fr.cate="";
        }else{
            fr.cate = cate;
        }
        return fr;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_news, container, false);
        initview(v);
        return v;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initdata();
    }
    public String getnowtime(){
        Date date = new Date(System.currentTimeMillis());//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String str = df.format(date);//获取String类型的时间
        return str;
    }

    private void getNews(String size,String startDate,String endDate,String words,String categories,String page,boolean isRefresh) {
        Call<NewsWhole> callback = Api.getInstance().getApi().getCall(size,startDate,endDate,words,categories,page);
        callback.enqueue(new Callback<NewsWhole>() {
            @Override
            public void onResponse(Call<NewsWhole> call, retrofit2.Response<NewsWhole> aws) {
                if(isRefresh) {
                    rfL.finishRefresh(true);
                } else {
                    rfL.finishLoadMore(true);
                }
                if(aws.isSuccessful()){
                    List<NewsData> list = aws.body().getData();
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
                    Toast.makeText(getActivity(),"获取失败",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<NewsWhole> call, Throwable t) {
                if(isRefresh) {
                    rfL.finishRefresh(true);
                } else {
                    rfL.finishLoadMore(true);
                }
                Toast.makeText(getActivity(),"获取新闻列表失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
    void initdata(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(newsAdapter);

        rfL.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout rfl) {
                rfl.finishLoadMore(2000/*,false*/);
                Num = 1;
                getNews( String.valueOf(50),"",getnowtime(),"",cate,String.valueOf(1),true);
            }
        });

        rfL.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout rfl) {
                rfl.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                Num+=1;
                getNews( String.valueOf(50),"",getnowtime(),"",cate,String.valueOf(Num),false);
            }
        });
        getNews( String.valueOf(10),"",getnowtime(),"",cate,String.valueOf(1),true);
    }
    void initview(View view){
        recyclerView = view.findViewById(R.id.RecyclerView);
        rfL = view.findViewById(R.id.refreshLayout);
    }
}