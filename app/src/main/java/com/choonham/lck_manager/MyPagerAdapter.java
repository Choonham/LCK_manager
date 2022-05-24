package com.choonham.lck_manager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStateAdapter {

    ArrayList<Fragment> items = new ArrayList<Fragment>();

    public MyPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getItem(position);
    }

    public void addItem(Fragment item) {
        items.add(item);
    }

    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public CharSequence getPageTitle(int position) {
        return "페이지" +  position;
    }
}
