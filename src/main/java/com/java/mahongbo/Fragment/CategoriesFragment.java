package com.java.mahongbo.Fragment;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.java.mahongbo.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CategoriesFragment extends BottomSheetDialogFragment{
    private ImageButton back;
    private TagFlowLayout flowup;
    private TagFlowLayout flowdown;

    private List<String> tagup = new ArrayList<>();
    private List<String> tagdown = new ArrayList<>();
    private DL mDL;

    public static CategoriesFragment newInstance(List<String> tagsOn, List<String> tagsOff) {
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.tagup = tagsOn;
        fragment.tagdown = tagsOff;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        initview(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initdata();
    }
    public interface DL {
        void DC(List<String> tagsOn, List<String> tagsOff);
    }
    public void setDataChangeListener(DL dL) {
        mDL = dL;
    }
    public void onDestroy() {
        mDL.DC(tagup, tagdown);
        super.onDestroy();
    }
    private class myAdapter extends TagAdapter<String> {

        public myAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, String str) {
            TextView view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tag, parent,false);
            view.setText(str);
            if(str == "头条") {
                view.setTextColor(Color.RED);
            } else {
                view.setTextColor(Color.BLACK);
            }
            return view;
        }

    }
    void initview(View v){
        back = v.findViewById(R.id.__back);
        flowup = (TagFlowLayout) v.findViewById(R.id.flowup);
        flowdown = (TagFlowLayout) v.findViewById(R.id.flowdown);


    }
    void initdata(){
        myAdapter Adapterdown = new myAdapter(tagdown);
        myAdapter Adapterup = new myAdapter(tagup);
        flowdown.setAdapter(Adapterdown);
        flowup.setAdapter(Adapterup);
        flowdown.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                tagup.add(tagdown.get(position));
                tagdown.remove(position);
                Adapterup.notifyDataChanged();
                Adapterdown.notifyDataChanged();
                return true;
            }
        });
        flowup.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                if(position == 0) {
                    return true;
                }
                tagdown.add(tagup.get(position));
                tagup.remove(position);
                Adapterup.notifyDataChanged();
                Adapterdown.notifyDataChanged();
                return true;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
