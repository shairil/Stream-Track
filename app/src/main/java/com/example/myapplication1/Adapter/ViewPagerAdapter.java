package com.example.myapplication1.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList;
    private ArrayList<String> titles;
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        this.fragmentArrayList = new ArrayList<>();
        this.titles = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(String title, Fragment fm){
        fragmentArrayList.add(fm);
        titles.add(title);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int pos){
        return titles.get(pos);
    }
}
