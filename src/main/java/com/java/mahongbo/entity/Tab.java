package com.java.mahongbo.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

public class Tab implements CustomTabEntity {
    public String tit;
    public int selected;
    public int unSelected;

    public Tab(String title, int selected, int unSelected) {
        this.tit = tit;
        this.selected = selected;
        this.unSelected = unSelected;
    }

    @Override
    public String getTabTitle() {
        return tit;
    }

    @Override
    public int getTabSelectedIcon() {
        return selected;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelected;
    }
}
