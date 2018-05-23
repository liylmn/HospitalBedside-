package com.zbiti.hospitalbedside.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 主页Tab适配器
 *
 * @author hp
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


}
