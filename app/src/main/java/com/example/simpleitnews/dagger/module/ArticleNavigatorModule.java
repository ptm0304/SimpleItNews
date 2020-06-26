package com.example.simpleitnews.dagger.module;

import com.example.simpleitnews.util.ArticleNavigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ArticleNavigatorModule {
    @Singleton
    @Provides
    ArticleNavigator provideArticleNavigator(ArticleNavigator articleNavigator) {
        return articleNavigator;
    }
}
