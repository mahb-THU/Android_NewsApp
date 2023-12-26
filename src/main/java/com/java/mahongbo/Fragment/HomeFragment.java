package com.java.mahongbo.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.java.mahongbo.R;
import com.java.mahongbo.SearchActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HomeFragment extends Fragment implements CategoriesFragment.DL{
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private List<String> myup=new ArrayList<>();
    private List<String> mydown=new ArrayList<>();
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private ImageButton imageButton;
    private Button search;
    private MyPagerAdapter myPagerAdapter;
    @Override
    public void DC(List<String> TitlesOn, List<String> TitlesOff) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        for (int i=0;i<fragments.size();i++) {
            ft.remove(fragments.get(i));
        }
        ft.commitNow();
        fragments.clear();
        for(int i=0;i<myup.size();i++){
            fragments.add(NewsFragment.newInstance(myup.get(i)));
        }
        viewPager.setAdapter(myPagerAdapter);
        slidingTabLayout.notifyDataSetChanged();
        slidingTabLayout.onPageSelected(0);
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        String[] title = {
                "头条", "娱乐", "军事", "教育", "文化",
                "健康", "财经", "体育", "汽车", "科技", "社会"};
        Collections.addAll(fragment.myup,title);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initview(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(String title : myup){
            fragments.add(NewsFragment.newInstance(title));
        }
        viewPager.setOffscreenPageLimit(fragments.size());
        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        slidingTabLayout.setViewPager(viewPager);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transformdata();
            }
        });
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return myup.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
    public void transformdata(){
        CategoriesFragment mBottomCategory = CategoriesFragment.newInstance(myup,mydown);
        View _view = getLayoutInflater().inflate(R.layout.fragment_category, null);
        mBottomCategory.setDataChangeListener(HomeFragment.this);
        mBottomCategory.show(getActivity().getSupportFragmentManager(), "bottom_category");
    }
    public void initview(View view){
        viewPager = view.findViewById(R.id.vp);
        slidingTabLayout = view.findViewById(R.id.SlidingTabLayout);
        search=view.findViewById(R.id.search_btn);
        imageButton=view.findViewById(R.id.ib);
        imageButton.setImageResource(R.drawable.copy);
        TextView title=view.findViewById(R.id.headtitle);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "character.ttf");
        title.setTypeface(typeface);
    }
}