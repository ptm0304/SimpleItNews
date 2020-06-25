package com.example.simpleitnews.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.example.simpleitnews.R;
import com.example.simpleitnews.databinding.ActivityMainBinding;
import com.example.simpleitnews.databinding.ActivityNewsWebViewBinding;

public class NewsWebViewActivity extends AppCompatActivity {

    private ActivityNewsWebViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_news_web_view);
        mBinding.setLifecycleOwner(this);
        WebSettings settings = mBinding.newsWebview.getSettings();

        // Denied starting an intent without a user gesture Webview Android
        // 사용자 제스처와 관계없는 url 변경이 제한된 점을 해결하기 위한 코드
        mBinding.newsWebview.setWebViewClient(new WebViewClient());

        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mBinding.newsWebview.loadUrl(getIntent().getExtras().getString("url"));
    }
}
