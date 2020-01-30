package com.mjgallery.mjgallery.mvp.ui.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class FmPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public FmPagerAdapter(List<Fragment> fragmentList, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList != null && !fragmentList.isEmpty() ? fragmentList.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
