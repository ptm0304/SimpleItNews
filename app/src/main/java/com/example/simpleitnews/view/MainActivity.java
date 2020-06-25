package com.example.simpleitnews.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.simpleitnews.R;
import com.example.simpleitnews.adapter.MainPagerAdapter;
import com.example.simpleitnews.databinding.ActivityMainBinding;
import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.util.ArticleNavigator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements ArticleNavigator {
    private ActivityMainBinding mBinding;
    private final String[] mTabTitles = {"News", "Bookmark"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setLifecycleOwner(this);

        mBinding.mainViewPager.setAdapter(new MainPagerAdapter(this, 2));

        new TabLayoutMediator(mBinding.mainTabLayout, mBinding.mainViewPager,
                (tab, position) -> tab.setText(mTabTitles[position])).attach();
    }

    @Override
    public void navigateArticle(NewsDto news) {
        if (news != null) {
            Intent intent = new Intent(getApplicationContext(), NewsWebViewActivity.class);
            intent.putExtra("url", news.getLink());
            startActivity(intent);
        }
    }
}
