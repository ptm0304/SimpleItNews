package com.example.simpleitnews.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.simpleitnews.R
import com.example.simpleitnews.adapter.MainPagerAdapter
import com.example.simpleitnews.databinding.ActivityMainBinding
import com.example.simpleitnews.model.dto.NewsDto
import com.example.simpleitnews.util.ArticleNavigator
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy

class MainActivityKt : AppCompatActivity(),
    ArticleNavigator {

    private lateinit var mBinding: ActivityMainBinding
    private val mTabTitles = arrayOf("News", "Bookmark")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.lifecycleOwner = this

        mBinding.mainViewPager.adapter = MainPagerAdapter(
            this, 2
        )
        TabLayoutMediator(
            mBinding.mainTabLayout, mBinding.mainViewPager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = mTabTitles[position]
            }
        ).attach()
    }

    override fun navigateArticle(news: NewsDto?) {
        news?.let {
            Intent(this, NewsWebViewActivity::class.java).apply {
                putExtra("url", news.link)
            }.run { startActivity(this) }
        }
    }
}
