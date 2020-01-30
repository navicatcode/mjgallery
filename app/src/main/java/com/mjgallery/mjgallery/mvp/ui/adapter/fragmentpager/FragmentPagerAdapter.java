package com.mjgallery.mjgallery.mvp.ui.adapter.fragmentpager;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
    ArrayList<Fragment> mFragments;
    String[] mTitles;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments, String[] mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }
}
