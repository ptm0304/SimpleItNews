package com.example.simpleitnews.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.simpleitnews.view.BookmarkFragment;
import com.example.simpleitnews.view.NewsFragment;


public class MainPagerAdapter extends FragmentStateAdapter {
    private int mNumTabs;

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity, int numTabs) {
        super(fragmentActivity);
        mNumTabs = numTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NewsFragment();
            case 1:
                return new BookmarkFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mNumTabs;
    }
}
