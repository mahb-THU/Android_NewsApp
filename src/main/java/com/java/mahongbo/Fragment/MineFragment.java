package com.java.mahongbo.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.java.mahongbo.CollectActivity;
import com.java.mahongbo.R;
import com.java.mahongbo.ReadActivity;


public class MineFragment extends Fragment {
    RelativeLayout collect,read;
    public MineFragment() {
        // Required empty public constructor
    }
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_mine, container, false);
        collect=mRootView.findViewById(R.id.rl_collect);
        read=mRootView.findViewById(R.id.rl_read);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),CollectActivity.class);
                startActivity(intent);
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ReadActivity.class);
                startActivity(intent);
            }
        });
        return mRootView;
    }

}