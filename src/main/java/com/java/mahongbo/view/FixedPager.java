package com.java.mahongbo.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class FixedPager extends ViewPager {
    public FixedPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedPager(@NonNull Context context) {
        super(context);
    }


    @Override
    public void setCurrentItem(int it)  {
        super.setCurrentItem(it, false);
    }
}
