package com.example.simpleitnews.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.simpleitnews.BaseApplication;
import com.example.simpleitnews.dagger.NewsFragmentComponent;
import com.example.simpleitnews.util.ArticleNavigator;
import com.example.simpleitnews.view.BookmarkFragment;
import com.example.simpleitnews.view.NewsFragment;


public class MainPagerAdapter extends FragmentStateAdapter {
    private int mNumTabs;
    private NewsFragmentComponent component;

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity, int numTabs) {
        super(fragmentActivity);
        mNumTabs = numTabs;

//        ArticleNavigator navigator = (ArticleNavigator) fragmentActivity;

        component = ((BaseApplication) fragmentActivity.getApplication())
                .getAppComponent()
                .getNewsFragmentComponent();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                NewsFragment newsFragment = new NewsFragment();
                component.inject(newsFragment);
                return newsFragment;
            case 1:
                BookmarkFragment bookmarkFragment = new BookmarkFragment();
                component.inject(bookmarkFragment);
                return bookmarkFragment;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mNumTabs;
    }
}
