package com.java.mahongbo;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.java.mahongbo.Fragment.MineFragment;
import com.java.mahongbo.entity.Tab;
import com.java.mahongbo.Fragment.HomeFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Context mycontext;
    private String[] mytitles = {"首页", "我的"};
    private int[] myiconUnselectIds = {R.drawable.home,  R.drawable.person};
    private int[] myiconSelectIds = {R.drawable.home,  R.drawable.person};

    private ViewPager myviewPager;
    private ArrayList<Fragment> myfragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mytabEntities = new ArrayList<>();
    private CommonTabLayout commonTabLayout;
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mycontext=this;
        setContentView( R.layout.activity_home);
        initData();
        initListener();
    }
    public void initData() {
        myviewPager = findViewById(R.id.viewPager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }
    public void initListener() {
        myfragments.add(HomeFragment.newInstance());
        myfragments.add(MineFragment.newInstance());
        for (int i = 0; i < mytitles.length; i++) {
            mytabEntities.add(new Tab(mytitles[i], myiconSelectIds[i], myiconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mytabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                myviewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        myviewPager.setOffscreenPageLimit(myfragments.size());
        myviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myviewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }
    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return myfragments.size();
        }
        @Override
        public Fragment getItem(int pos) {
            return myfragments.get(pos);
        }
        @Override
        public CharSequence getPageTitle(int pos) {
            return mytitles[pos];
        }


    }

}