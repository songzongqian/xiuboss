package com.byx.xiuboss.xiuboss.Mvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by TauchMe on 2018/7/18.
 */

public class OrderPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> tabString;
    private ArrayList<Fragment> fragments;


    public OrderPagerAdapter(FragmentManager fm, ArrayList<String> tabString, ArrayList<Fragment> fragments) {
        super(fm);
        this.tabString = tabString;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabString.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabString.get(position);
    }


}
