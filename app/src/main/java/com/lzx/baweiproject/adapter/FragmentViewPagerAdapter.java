package com.lzx.baweiproject.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    public FragmentViewPagerAdapter( FragmentManager fm, List<Fragment> _fragments) {
        super(fm);
        this.fragmentList=_fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }
}
